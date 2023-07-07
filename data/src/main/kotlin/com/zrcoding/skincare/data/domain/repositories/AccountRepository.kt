package com.zrcoding.skincare.data.domain.repositories

import android.net.Uri
import com.zrcoding.skincare.data.domain.model.Account

interface AccountRepository {

    /**
     * Get the local account if already stored.
     *
     * @return the latest [Account] version.
     */

    suspend fun getLocalAccount(): Account?

    /**
     * Get the account from remote.
     *
     * @return the latest [Account] version.
     */
    suspend fun getRemoteAccount(): Account

    /**
     * Uploads the new image the server.
     *
     * @return the Url to the remote image
     */
    suspend fun uploadNewImage(imageUri: Uri): String

    /**
     * Updates the account.
     *
     * @return the latest [Account] version after update.
     */
    suspend fun updateAccount(account: Account): Account
}