import org.junit.Test

import org.junit.Assert.*

class ChatServiceTest {

    @Test
    fun createMessage_noChat() {
        val service = ChatService()
        service.createMessage(45, 23, "msg 45-23")
        assertNotEquals(service.counterOfChats, 1)
        assertNotEquals(service.counterOfMessages, 1)
        assertNotEquals(service.listOfChats.size, 0)
    }

    @Test
    fun editMessage_noChat() {
        val service = ChatService()
        val msg = service.createMessage(45, 23, "msg 45-23")
        val chatSizeBefore = service.getMessagesByChatId(service.findChatId(45, 23)).size
        service.editMessage(msg, "23-45")
        val chatSizeAfter = service.getMessagesByChatId(service.findChatId(45, 23)).size
        assertEquals(chatSizeBefore, chatSizeAfter)
    }

    @Test
    fun createChat_deleteChat() {
        val service = ChatService()
        val chat = service.createChat(45, 23)
        service.createChat(44, 22)
        service.createMessage(45, 23, "msg 1")
        service.createMessage(45, 23, "msg 2")
        service.createMessage(45, 23, "msg 3")
        service.createMessage(44, 22, "msg 1")
        service.createMessage(44, 22, "msg 2")
        val chatsSizeBefore = service.listOfChats.size
        service.deleteChat(chat)
        val chatsSizeAfter = service.listOfChats.size
        assertEquals(chatsSizeAfter, chatsSizeBefore - 1)
    }

    @Test
    fun deleteMessage_notLast() {
        val service = ChatService()
        service.createMessage(45, 23, "msg 1")
        val msg = service.createMessage(45, 23, "msg 2")
        service.createMessage(45, 23, "msg 3")
        val chatSizeBefore = service.getMessagesByChatId(service.findChatId(45, 23)).size
        service.deleteMessage(msg)
        val chatSizeAfter = service.getMessagesByChatId(service.findChatId(45, 23)).size
        assertEquals(chatSizeBefore - 1, chatSizeAfter)
    }

    @Test
    fun getUnreadChatsCount() {
        val service = ChatService()
        val msg1 = service.createMessage(45, 23, "msg 1")
        service.createMessage(23, 45, "msg 2")
        service.createMessage(45, 22, "msg 3")
        service.createMessage(44, 22, "msg 4")
        service.createMessage(23, 22, "msg 5")
        val msg6 = service.createMessage(45, 2, "msg 6")
        val msg7 = service.createMessage(45, 222, "msg 7")
        service.readMessage(msg1)
        service.readMessage(msg6)
        service.readMessage(msg7)
        assertEquals(service.getUnreadChatsCount(45), 2)

    }

    @Test
    fun getMessagesByChatId() {
        val service = ChatService()
        service.createMessage(45, 23, "msg 1")
        service.createMessage(23, 45, "msg 2")
        service.createMessage(45, 22, "msg 3")
        service.createMessage(44, 22, "msg 4")
        service.createMessage(23, 22, "msg 5")
        service.createMessage(45, 23, "msg 6")
        service.createMessage(45, 23, "msg 7")

        assertEquals(service.getMessagesByChatId(1).size, 4)
    }

    @Test fun getMessagesByLastMessageId (){
        val service = ChatService()
        service.createMessage(45, 23, "msg 1")
        service.createMessage(23, 45, "msg 2")
        service.createMessage(45, 22, "msg 3")
        service.createMessage(44, 22, "msg 4")
        service.createMessage(23, 22, "msg 5")

        assertEquals(service.getMessagesByLastMessageId(1, 1).size, 2)
    }

    @Test
    fun getMessagesByQuantity (){
        val service = ChatService()
        service.createMessage(45, 23, "msg 1")
        service.createMessage(23, 45, "msg 2")
        service.createMessage(45, 22, "msg 3")
        service.createMessage(44, 22, "msg 4")
        service.createMessage(23, 22, "msg 5")

        assertEquals(service.getMessagesByQuantity(2, 1).size, 2)
    }
}
