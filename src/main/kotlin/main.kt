fun main() {
    val service = ChatService ()
    // service.createChat(1,2)
    //val k = service.createChat(4,5)
    //println(service)
//    println(service.ifIsChat(2,1))
//    println(service.ifIsChat(1,5))

//    val y = service.createMessage(4,5,"4-5")
//    val o = service.createMessage(1,2,"1-2")
    service.createMessage(5, 8, "1-5-8")
    service.createMessage(5, 8, "2-5-8")
    val u = service.createMessage(5, 8, "5-8")
    println(service)
//    val u1 = service.createMessage(2,5,"5-8")
//    val u2 = service.createMessage(5,8,"5-8")
//    val u3 = service.createMessage(5,8,"5-8")
    //println(service.listOfChats)
    //println(service.getMessagesByLastMessageId(5,3))
    //println(service.getMessagesByQuantity(2,3))
    // println(service.getMessagesByChatId(3))
    // println(service.listOfChats)
//    println(service)
//
//    service.deleteChat(k)
//    println(service)
//
//    service.deleteMessage(o)
//    println(service)
//
    println(service.getMessagesByChatId(service.findChatId(5,8)).size)
    service.editMessage(u, "8---5")
    println(service)
    println(service.getMessagesByChatId(service.findChatId(5,8)).size)

//    println(service)

    // println(service.getChats(5))

//    service.readMessage(y)
//    println(service.getUnreadChatsCount(5))
//    service.readMessage(u1)
//    println(service.getUnreadChatsCount(5))
}