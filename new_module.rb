#! ruby
require 'lib/xcopy'

def del(file)
  if File.exist? file
    File.delete file
    puts "deleted #{file}"
  end
end

def change_lines(file_name, source, target)
  return unless File.file? file_name
  lines = IO.readlines(file_name)
  new_lines = lines.map { |line| line.gsub(source, target) }

  if new_lines != lines
    save_file(file_name, new_lines)
  end
end

def save_file(file_name, new_lines)
  File.open(file_name, "w") { |file|
    new_lines.each { |line| file.print line }
  }
  puts "changed file #{file_name}"
end

def change_pom(file_name, target)
  return unless File.file? file_name
  lines = IO.readlines(file_name)
  add = "        <module>#{target}</module>\n"
  if lines.find_all { |line| line =~ /<module>#{target}<\/module>/ }.size > 0
    puts "#{file_name} has module #{target}"
    return
  end

  new_lines = lines.map do |line|
    if line =~ /<\/modules>/
      line = add + line
    else
      line
    end
  end

  if new_lines != lines
    save_file(file_name, new_lines)
  end
end

if __FILE__ == $0

  if ARGV.length < 1 or ARGV.include?('-help')
    puts "copy module 'playground' to new module <name>"
    exit
  end

  source = 'playground'
  target = ARGV[0]

  puts "--- copy template"

  if ! File.exist? target
    Dir.chdir source
    `mvn clean`
    Dir.chdir '..'

    Xcopy.copy_tree(source, target)
    puts "copied #{source} to #{target}"
  else
    puts "#{target} exists"
  end

  puts "--- delete doc"

  Dir.chdir target
  del 'micronaut-cli.yml'
  Dir['*.URL'].each { |file| del(file) }
  Dir.chdir '..'

  puts "--- change paths"
  Dir.chdir "#{target}/src/main/java/org/codecop/monolith"
  if File.exist? source
    Xcopy.move_tree(source, target)
    Dir.delete(source)
    puts "moved src/main/... #{source} to #{target}"
  else
    puts "src/main/... #{target} exists"
  end
  Dir.chdir "../../../../../../.."
  Dir.chdir "#{target}/src/test/java/org/codecop/monolith"
  if File.exist? source
    Xcopy.move_tree(source, target)
    Dir.delete(source)
    puts "moved test/main/... #{source} to #{target}"
  else
    puts "src/test/... #{target} exists"
  end
  Dir.chdir "../../../../../../.."

  puts "--- change files"

  Dir.chdir target
  (Dir['**/.*'] + Dir['**/*.*']).each do |file|
    change_lines(file, source, target)
  end
  Dir.chdir '..'

  puts "--- add to parent pom"

  change_pom('pom.xml', target)

end
