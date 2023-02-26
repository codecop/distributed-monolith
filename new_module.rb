#! ruby
require 'lib/xcopy'

def del(file)
  if File.exist? file
    File.delete file
  end
end

def change_lines(file, source, target)
  
end

if __FILE__ == $0

  if ARGV.length < 1 or ARGV.include?('-help')
    puts "copy playground to new module"
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
  end

  puts "--- delete doc"
  
  Dir.chdir target
  del 'micronaut-cli.yml'
  Dir['*.URL'].each { |file| del(file) }
  Dir.chdir '..'

  puts "--- change paths"

  puts "--- change files"

  Dir.chdir target
  Dir['**/*.*'].each do |file|
    change_lines(file, source, target)
  end
  Dir.chdir '..'

  puts "--- add to parent pom"

end
