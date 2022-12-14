package az.khayalsharifli.bankrespublika.data.repository

import az.khayalsharifli.bankrespublika.data.local.LocalDataSource
import az.khayalsharifli.bankrespublika.data.local.LocalDtoItem
import az.khayalsharifli.bankrespublika.data.mapper.MoneyMapper
import az.khayalsharifli.bankrespublika.data.remote.MoneyService
import kotlinx.coroutines.flow.Flow

class MoneyRepositoryImpl(
    private val service: MoneyService,
    private val localDataSource: LocalDataSource,
    private val moneyMapper: MoneyMapper
) : MoneyRepository {

    override fun observeMoney(): Flow<List<LocalDtoItem>> {
        return localDataSource.observeMoney()
    }

    override suspend fun sync() {
        val remote = service.getMoney()
        val local = remote.map { moneyMapper.fromRemoteToLocal(it) }
        localDataSource.insertMoney(local)
    }
}