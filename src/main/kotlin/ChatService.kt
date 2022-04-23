data class ChatService(
    var listOfChats: MutableList<Chat> = mutableListOf(),
    var counterOfChats: Int = 1,
    var counterOfMessages: Int = 1
) {
    fun createMessage(userIdFrom: Int, userIdTo: Int, text: String): Message {
        val chat = listOfChats.firstOrNull { it.chatId == findChatId(userIdFrom, userIdTo) } ?: createChat(
            userIdFrom,
            userIdTo
        )
        val newMessage = Message(messageId = counterOfMessages++, text = text, chatId = chat.chatId)
        chat.messages.add(newMessage)
        return newMessage
    }

    fun editMessage(message: Message, newText: String) {
        val chat = listOfChats.first { it.chatId == message.chatId }
        chat.messages[chat.messages.indexOf(message)] = message.copy(text = newText, ifUnread = true)
    }

    fun deleteChat(chat: Chat) {
        chat.messages.clear()
        listOfChats.remove(chat)  //listOfChats = listOfChats.filterNot { it.chatId == chat.chatId }.toMutableList()
    }

    fun deleteMessage(message: Message) {
        val chat = listOfChats.first { it.chatId == message.chatId }
        chat.messages.remove(message)
        chat.messages.ifEmpty { deleteChat(chat) }
    }

    fun findChatId(userId1: Int, userId2: Int): Int {
        return listOfChats.find { it.userIds.contains(userId1) && it.userIds.contains(userId2) }?.chatId
            ?: 0
    }

    fun createChat(userId1: Int, userId2: Int): Chat {
        val newChat = Chat(userIds = setOf(userId1, userId2), chatId = counterOfChats++)
        listOfChats.add(newChat)
        return newChat
    }

    private fun getChats(userId: Int): List<Chat> {
        return listOfChats.filter { it.userIds.contains(userId) }
    }

    fun readMessage(message: Message): Message {
        val chat = listOfChats.first { it.chatId == message.chatId }
        val newMessage = message.copy(ifUnread = false)
        chat.messages[chat.messages.indexOf(message)] = newMessage
        return newMessage
    }


    fun getUnreadChatsCount(userId: Int): Int {
        return getChats(userId).filter { chat -> chat.messages.any { it.ifUnread } }.size
    }

    fun getMessagesByChatId(chatId: Int): List<Message> {
        return listOfChats.first { it.chatId == chatId }.messages.map { it.copy(ifUnread = false) }
    }

    fun getMessagesByLastMessageId(messageId: Int, chatId: Int): List<Message> {
        return getMessagesByChatId(chatId).filter { it.messageId >= messageId }
    }

    fun getMessagesByQuantity(quantity: Int, chatId: Int): List<Message> {
        return getMessagesByChatId(chatId).takeLast(quantity)
    }
}


