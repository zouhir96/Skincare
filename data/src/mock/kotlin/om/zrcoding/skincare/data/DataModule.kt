package om.zrcoding.skincare.data

import com.zrcoding.skincare.data.domain.repositories.AccountRepository
import com.zrcoding.skincare.data.domain.repositories.AuthenticationRepository
import com.zrcoding.skincare.data.domain.repositories.CartRepository
import com.zrcoding.skincare.data.domain.repositories.CategoryRepository
import com.zrcoding.skincare.data.domain.repositories.ProductRepository
import com.zrcoding.skincare.data.domain.repositories.VolumeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import om.zrcoding.skincare.data.repositories.AccountRepositoryMockSuccess
import om.zrcoding.skincare.data.repositories.AuthenticationRepositoryMockSuccess
import om.zrcoding.skincare.data.repositories.CartRepositoryMockSuccess
import om.zrcoding.skincare.data.repositories.CategoryRepositoryMockSuccess
import om.zrcoding.skincare.data.repositories.ProductRepositoryMockSuccess
import om.zrcoding.skincare.data.repositories.VolumeRepositoryMockSuccess
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun provideProductRepository(
        productRepositoryMockSuccess: ProductRepositoryMockSuccess
    ): ProductRepository

    @Binds
    @Singleton
    abstract fun provideCategoryRepository(
        categoryRepositoryMockSuccess: CategoryRepositoryMockSuccess
    ): CategoryRepository

    @Binds
    @Singleton
    abstract fun provideVolumeRepository(
        volumeRepositoryMockSuccess: VolumeRepositoryMockSuccess
    ): VolumeRepository

    @Binds
    @Singleton
    abstract fun provideCartRepository(
        cartRepositoryMockSuccess: CartRepositoryMockSuccess
    ): CartRepository

    @Binds
    @Singleton
    abstract fun provideAccountRepository(
        accountRepositoryMockSuccess: AccountRepositoryMockSuccess
    ): AccountRepository

    @Binds
    @Singleton
    abstract fun provideAuthenticationRepository(
        authenticationRepositoryMockSuccess: AuthenticationRepositoryMockSuccess
    ): AuthenticationRepository
}