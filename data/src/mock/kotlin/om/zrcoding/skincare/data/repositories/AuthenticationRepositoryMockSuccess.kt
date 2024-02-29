package om.zrcoding.skincare.data.repositories

import com.zrcoding.skincare.data.domain.model.GENDER
import com.zrcoding.skincare.data.domain.model.Token
import com.zrcoding.skincare.data.domain.repositories.AuthenticationRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class AuthenticationRepositoryMockSuccess @Inject constructor() : AuthenticationRepository {
    override suspend fun login(email: String, password: String): Token {
        delay(1000)
        return Token(
            accessToken = "AAAA-BBBB-CCCC",
            refreshToken = "DDDD-EEEE-FFFF"
        )
    }

    override suspend fun signup(email: String, password: String): Token {
        delay(1000)
        return Token(
            accessToken = "AAAA-BBBB-CCCC",
            refreshToken = "DDDD-EEEE-FFFF"
        )
    }

    override suspend fun completeAccount(fullName: String, age: Int, gender: GENDER): Boolean {
        delay(1000)
        return true
    }
}