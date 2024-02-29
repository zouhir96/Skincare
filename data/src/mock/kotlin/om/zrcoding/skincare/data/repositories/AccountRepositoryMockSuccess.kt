package om.zrcoding.skincare.data.repositories

import android.net.Uri
import com.zrcoding.skincare.data.domain.model.Account
import com.zrcoding.skincare.data.domain.repositories.AccountRepository
import kotlinx.coroutines.delay
import javax.inject.Inject


class AccountRepositoryMockSuccess @Inject constructor() : AccountRepository {

    companion object {
        var account = Account.fake
    }

    override suspend fun getLocalAccount(): Account {
        delay(100)
        return account
    }

    override suspend fun getRemoteAccount(): Account {
        delay(100)
        return account
    }

    override suspend fun uploadNewImage(imageUri: Uri): String {
        // return another image url just for mock.
        val newImageUrl =
            "https://environskincare.com/za/wp-content/uploads/2017/03/Skin-Care-Mistakes-that-are-harming-your-skin-Environ-Skin-Care.jpg"
        account = account.copy(imageUrl = newImageUrl)
        return newImageUrl
    }

    override suspend fun updateAccount(account: Account): Account {
        delay(100)
        return Companion.account
    }
}