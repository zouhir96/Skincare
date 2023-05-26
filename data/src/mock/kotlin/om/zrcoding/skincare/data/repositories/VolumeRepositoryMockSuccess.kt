package om.zrcoding.skincare.data.repositories

import com.zrcoding.skincare.data.domain.repositories.VolumeRepository
import com.zrcoding.skincare.data.sources.fake.fakeVolumes
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class VolumeRepositoryMockSuccess @Inject constructor() : VolumeRepository {
    override fun getAll() = flowOf(fakeVolumes)

    override suspend fun getOne(uuid: String) = fakeVolumes.find { it.uuid == uuid }
}