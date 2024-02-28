package upv.dadm.jsonplaceholder.model

data class User(
    val id: String,
    val name: String,
    val addressFirstLine: String,
    val addressSecondLine: String,
    val email: String,
    val phone: String
)
