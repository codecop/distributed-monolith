#! ruby
require 'lib/xcopy'

def copy_template(source, target)
  puts '--- copy template:'

  if ! File.exist? target
    clean_module(source)
    Xcopy.copy_tree(source, target)
    puts "> copied #{source} to #{target}"
  else
    puts "> #{target} exists"
  end
end

def clean_module(source)
  Dir.chdir source
  `mvn clean`
  Dir.chdir '..'
end

def delete_docs(source, target)
  puts '--- delete docs:'

  Dir.chdir target
  delete_file 'micronaut-cli.yml'
  Dir['*.URL'].each { |file| delete_file(file) }
  Dir.chdir '..'
end

def delete_file(file)
  if File.exist? file
    File.delete file
    puts "> deleted #{file}"
  end
end

def change_packages(source, target)
  puts '--- change packages:'

  Dir.chdir target
  change_package_in('src/main/java', source, target)
  change_package_in('src/test/java', source, target)
  Dir.chdir '..'
end

Base_package = 'org/codecop/monolith'

def change_package_in(folder, source, target)
  Dir.chdir "#{folder}/#{Base_package}"
  if File.exist? source
    Xcopy.move_tree(source, target)
    Dir.delete(source)
    puts "> moved #{folder}/.../#{source} to #{target}"
  else
    puts "> #{folder}/.../#{target} exists"
  end
  Dir.chdir '../../../../../..'
end

def change_files(source, target)
  puts '--- change files:'

  Dir.chdir target
  (Dir['**/.*'] + Dir['**/*.*']).each do |file|
    change_lines(file, source, target)
  end
  Dir.chdir '..'
end

def change_lines(file_name, source, target)
  return unless File.file? file_name
  lines = IO.readlines(file_name)
  new_lines = lines.map { |line| line.gsub(source, target) }
  save_if_changed(file_name, lines, new_lines)
end

def save_if_changed(file_name, lines, new_lines)
  if new_lines != lines
    save_file(file_name, new_lines)
    puts "> changed file #{file_name}"
  end
end

def save_file(file_name, new_lines)
  File.open(file_name, 'w') { |file|
    new_lines.each { |line| file.print line }
  }
end

def change_pom(target)
  puts '--- add to parent pom:'

  file_name = 'pom.xml'
  return unless File.file? file_name
  lines = IO.readlines(file_name)

  if lines.find_all { |line| line =~ /<module>#{target}<\/module>/ }.size > 0
    puts "> #{file_name} has module #{target}"
    return
  end
  new_lines = add_module(lines, target)

  save_if_changed(file_name, lines, new_lines)
end

def add_module(lines, target)
  add = "        <module>#{target}</module>\n"
  lines.map do |line|
    if line =~ /<\/modules>/
      puts "> added module #{target}"
      line = add + line
    else
      line
    end
  end
end

if __FILE__ == $0

  if ARGV.length < 1 or ARGV.length > 2 or ARGV.include?('-help')
    puts 'ruby new_module.rb <new-module-name> [<source module name>|playground]'
    puts 'copy module \'playground\' to new module <name>'
    exit
  end

  target = ARGV[0]
  if ARGV.length < 2
    source = 'playground'
  else
    source = ARGV[1]
  end

  copy_template(source, target)
  delete_docs(source, target)
  change_packages(source, target)
  change_files(source, target)
  change_pom(target)

end
