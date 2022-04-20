data class ChatService(
    var listOfChats: MutableList<Chat> = mutableListOf(),
    var counterOfChats: Int = 1,
    var counterOfMessages: Int = 1
) {
    fun createMessage(userIdFrom: Int, userIdTo: Int, text: String): Message {
        val chat = if (ifIsChat(userIdFrom, userIdTo)) {
            listOfChats.first { it.chatId == findChatId(userIdFrom, userIdTo) }
        } else {
            createChat(userIdFrom, userIdTo)
        }
        val newMessage = Message(messageId = counterOfMessages++, text = text, chatId = chat.chatId)
        chat.messages += newMessage

        return newMessage
    }

    fun editMessage(message: Message, newText: String) {
        val chat = listOfChats.first { it.chatId == message.chatId }
        chat.messages[chat.messages.indexOf(message)] = message.copy(text = newText, ifUnread = true)
    }

    fun deleteChat(chat: Chat)  {
        chat.messages.clear()
        listOfChats.remove(chat)
    }


    fun deleteMessage(message: Message) {
        val chat = listOfChats.first { it.chatId == message.chatId }
        chat.messages.remove(message)
        chat.messages.ifEmpty { deleteChat(chat) }
    }

    private fun ifIsChat(userId1: Int, userId2: Int): Boolean =
        listOfChats.any() { it.userIds.contains(userId1) && it.userIds.contains(userId2) }

    fun findChatId(userId1: Int, userId2: Int): Int =
        listOfChats.find { it.userIds.contains(userId1) && it.userIds.contains(userId2) }?.chatId
            ?: 0

    fun createChat(userId1: Int, userId2: Int): Chat {
        val newChat = Chat(userIds = setOf(userId1, userId2), chatId = counterOfChats++)
        listOfChats += newChat
        return newChat
    }

    fun getChats(userId: Int): List<Chat> {
        return listOfChats.filter { it.userIds.contains(userId) }
    }

    private fun readMessage(message: Message): Message {
        val chat = listOfChats.first { it.chatId == message.chatId }
        val newMessage = message.copy(ifUnread = false)
        chat.messages[chat.messages.indexOf(message)] = newMessage
        return newMessage
    }

    fun getUnreadChatsCount(userId: Int): Int {
        val unreadChats = mutableListOf<Chat>()
        for (chat in getChats(userId)) {
            for (message in chat.messages) {
                if (message.ifUnread && !unreadChats.contains(chat)) unreadChats += chat
            }
        }
        return unreadChats.size
    }

    fun getMessagesByChatId(chatId: Int): List<Message> {
        val chatMessages = listOfChats.first { it.chatId == chatId }.messages
        for (message in chatMessages) {
            readMessage(message)
        }
        return chatMessages
    }


    fun getMessagesByLastMessageId(messageId: Int, chatId: Int): List<Message> {
        return getMessagesByChatId(chatId).filter { it.messageId >= messageId }

    }

    fun getMessagesByQuantity(quantity: Int, chatId: Int): List<Message> {
        return getMessagesByChatId(chatId).takeLast(quantity)
    }
}


