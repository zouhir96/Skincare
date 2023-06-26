package om.zrcoding.skincare.data.repositories

import com.zrcoding.skincare.data.domain.model.Account
import com.zrcoding.skincare.data.domain.repositories.AccountRepository
import kotlinx.coroutines.delay
import java.io.File
import javax.inject.Inject


class AccountRepositoryMockSuccess @Inject constructor() : AccountRepository {

    companion object {
        var account = Account.fake
    }

    override suspend fun getLocalAccount(): Account {
        delay(1000)
        return account
    }

    override suspend fun getRemoteAccount(): Account {
        delay(1000)
        return account
    }

    override suspend fun uploadNewImage(file: File): String {
        return "https://www.charlottetilbury.com/_next/static/images/Masterclass-placeholder-image-c9d6d1b2ca29a310fe41f6d12e1346d4.jpg"
    }

    override suspend fun updateAccount(account: Account): Account {
        delay(1000)
        return Companion.account
    }
}