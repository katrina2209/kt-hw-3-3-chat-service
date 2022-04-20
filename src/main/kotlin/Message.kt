data class Message(
    val messageId: Int = 0,
    val chatId: Int = 0,
    val text: String? = null,
    val ifUnread: Boolean = true

)
{

    override fun toString(): String {
        return "СООБЩЕНИЕ: messageId=$messageId, chatId=$chatId,  text=$text, ifUnread = $ifUnread"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Message) return false

        if (messageId != other.messageId) return false

        return true
    }

    override fun hashCode(): Int {
        return messageId
    }


}