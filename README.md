Visionist - by kwek20
======

Tencode competition plugin
Streaming at www.livestream.tv/kwek

Compiled with Spigot 1.7.2

Description:
Create moving fountains and falling buildings and such!


commands:
---------
1.  visionist:
    description: visionist base command
    usage: Type /visionist to see the info 
    aliases: [v]
    permission: visionist.help
2.  createstream:
    description: Create a stream of falling blocks at your location.
    usage: Type /createstream [MATERIAL] [amount] [byte] to create a stream at your location. last 2 optional. Goes up or down according to your eye target
    aliases: [creates, cstream] 
    permission: visionist.createstream
3.  disablestream:
    description: Disables the stream nearest to you
    usage: Type /disablestream to disable the stream nearest to you
    aliases: [ds, disables, dstream] 
    permission: visionist.disablestream
4.  enablestream:
    description: Enables the stream nearest to you
    usage: Type /enablestream to disable the stream nearest to you
    aliases: [ss, enables, estream] 
    permission: visionist.enablestream
5.  removestream:
    description: Removes the stream nearest to you
    usage: Type /removestream to remove the stream nearest to you
    aliases: [rs, removes, rstream] 
    permission: visionist.removestream
6.  createstructure:
    description: Creates a structure for use in falling structures!
    usage: Type /createstructure create [name] to create one! Make sure you set pos1 and pos2 first.
    aliases: [cstructure] 
    permission: visionist.createstructure
7.  fallingstructure:
    description: Creates a fallingstructure with a structure made before
    usage: Type /fallingstructure [name] to create one! 
    aliases: [fs, fallings, fstructure] 
    permission: visionist.fallingstructure
    
permissions:
------------
1.  visionist.*:
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
2.  visionist.reload: 
    description: Allows you to reload the visionist
3.  visionist.help:
    description: Allows you to view help
4.  visionist.fallingstructure: 
    description: Allows you to create fallingstructures
5.  visionist.createstream:
    description: Allows you to create streams
6.  visionist.disablestream:
    description: Allows you to disable streams
7.  visionist.enablestream:
    description: Allows you to enable streams
8.  visionist.removestream:
    description: Allows you to remvoe streams
9.  visionist.createstructure:
    description: Allows you to create structures