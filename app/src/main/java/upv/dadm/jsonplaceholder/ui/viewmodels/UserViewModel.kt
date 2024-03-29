package upv.dadm.jsonplaceholder.ui.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import upv.dadm.jsonplaceholder.model.User

class UserViewModel : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    private val _throwable = MutableStateFlow<Throwable?>(null)
    val throwable = _throwable.asStateFlow()

    fun getUserById(id: String) {
        _user.update {
            User(
                id = id,
                name = "${setOf("Alice", "Bob", "Charlie", "David").random()} ${
                    setOf(
                        "Smith",
                        "Brown",
                        "Lancaster",
                        "Johnson"
                    ).random()
                }",
                street = setOf(
                    "Cherry Lane",
                    "Helm Street",
                    "Trafalgar Square",
                    "Diagonal Alley"
                ).random(),
                suite = "${(1..20).random()}",
                zipcode = "${(100..999).random()} ${(100..999).random()}",
                city = setOf(
                    "Valencia",
                    "London",
                    "San Francisco",
                    "Sydney"
                ).random(),
                email = "${setOf("qwerty", "asdfg", "zxcvb", "poiuy").random()}@gmail.com",
                phone = "${(100..999).random()} ${(100..999).random()} ${(100..999).random()}"
            )

        }
    }

    fun addUser(newUser: User) {
        _user.update { newUser }
    }

    fun resetError() {
        _throwable.update { null }
    }
}