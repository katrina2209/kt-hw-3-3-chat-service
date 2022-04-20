data class Chat(
        val chatId: Int,
        val userIds: Set<Int>,
        var messages: MutableList<Message> = mutableListOf()
)
{
        override fun toString(): String {
                return "\nЧАТ: chatId=$chatId, userIds=$userIds, \n СООБЩЕНИЯ: $messages)"
        }
}