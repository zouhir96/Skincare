package om.zrcoding.skincare.data.repositories

import com.zrcoding.skincare.data.domain.model.Token
import com.zrcoding.skincare.data.domain.repositories.AuthenticationRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class AuthenticationRepositoryMockSuccess @Inject constructor() : AuthenticationRepository {
    override suspend fun login(email: String, password: String): Token {
        delay(3000)
        return Token(
            accessToken = "AAAA-BBBB-CCCC",
            refreshToken = "DDDD-EEEE-FFFF"
        )
    }

    override suspend fun signup(email: String, password: String): Token {
        delay(3000)
        return Token(
            accessToken = "AAAA-BBBB-CCCC",
            refreshToken = "DDDD-EEEE-FFFF"
        )
    }
}