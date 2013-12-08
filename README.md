Visionist - by kwek20
======

Tencode competition plugin
Streaming at www.livestream.tv/kwek

Compiled with Spigot 1.7.2

Description:
Create moving fountains and falling buildings and such!


commands:
  visionist:
    description: visionist base command
    usage: Type /visionist to see the info 
    aliases: [v]
    permission: visionist.help
  createstream:
    description: Create a stream of falling blocks at your location.
    usage: Type /createstream [MATERIAL] [amount] [byte] to create a stream at your location. last 2 optional. Goes up or down according to your eye target
    aliases: [creates, cstream] 
    permission: visionist.createstream
  disablestream:
    description: Disables the stream nearest to you
    usage: Type /disablestream to disable the stream nearest to you
    aliases: [ds, disables, dstream] 
    permission: visionist.disablestream
  enablestream:
    description: Enables the stream nearest to you
    usage: Type /enablestream to disable the stream nearest to you
    aliases: [ss, enables, estream] 
    permission: visionist.enablestream
  removestream:
    description: Removes the stream nearest to you
    usage: Type /removestream to remove the stream nearest to you
    aliases: [rs, removes, rstream] 
    permission: visionist.removestream
  createstructure:
    description: Creates a structure for use in falling structures!
    usage: Type /createstructure create [name] to create one! Make sure you set pos1 and pos2 first.
    aliases: [cstructure] 
    permission: visionist.createstructure
  fallingstructure:
    description: Creates a fallingstructure with a structure made before
    usage: Type /fallingstructure [name] to create one! 
    aliases: [fs, fallings, fstructure] 
    permission: visionist.fallingstructure
permissions:
  visionist.*:
    description: Gives access to all visionist commands
    children:
     visionist.help: true
     visionist.reload: true
     visionist.createstream: true
     visionist.disablestream: true
     visionist.enablestream: true
     visionist.removestream: true
     visionist.createstructure: true
     visionist.fallingstructure: true
  visionist.reload: 
    description: Allows you to reload the visionist
  visionist.help:
    description: Allows you to view help
  visionist.fallingstructure: 
    description: Allows you to create fallingstructures
  visionist.createstream:
    description: Allows you to create streams
  visionist.disablestream:
    description: Allows you to disable streams
  visionist.enablestream:
    description: Allows you to enable streams
  visionist.removestream:
    description: Allows you to remvoe streams
  visionist.createstructure:
    description: Allows you to create structures