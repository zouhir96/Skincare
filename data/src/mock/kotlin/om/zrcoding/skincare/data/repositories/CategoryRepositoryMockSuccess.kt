package om.zrcoding.skincare.data.repositories

import com.zrcoding.skincare.data.domain.repositories.CategoryRepository
import com.zrcoding.skincare.data.sources.fake.fakeCategories
import javax.inject.Inject

class CategoryRepositoryMockSuccess @Inject constructor() : CategoryRepository {

    override suspend fun getAll() = fakeCategories

    override suspend fun getOne(uuid: String) = fakeCategories.find { it.uuid == uuid }
}