package com.nemo.game.data;

import com.nemo.common.persist.Cacheable;
import com.nemo.game.back.entity.Announce;
import com.nemo.game.entity.*;
import com.nemo.game.entity.sys.SysData;

import java.util.List;

//数据提供者
public interface IDataProvider {
    //更新数据到磁盘 是否立即更新
    void updateData(Cacheable cache, boolean immediately);

    void updateData(long dataId, int dataType, boolean immediately);
    //从磁盘和缓存中删除一条数据 是否立即删除
    void deleteData(Cacheable cache, boolean immediately);
    //新增一条数据到磁盘和缓存 是否立即增加
    void insertData(Cacheable cache, boolean immediately);
    //增加一条数据到缓存
    void addData(Cacheable cache);
    //从缓存中移除一条数据
    void removeData(Cacheable cache);
    //获取用户数据 用户登录名 服务器id 平台id
    User getUser(String loginName, int sid, int pid);
    //获取用户数据
    User getUser(long id);

    Role getRole(long id);

    RoleBag getBag(long id);

    RoleMail getRoleMail(long id);

    RoleCount getCount(long id);

    <T extends SysData> T getSysData(long id, Class<T> clazz);

    void registerUser(User user);

    void store();

    Union getUnion(long id);

    Rank getRank(long id);

    RankHero getRankHero(long id);

    List<Rank> getRankList(String type, String timeType);

    List<RankHero> getRankHeroList(int career);
    //获取定时公告列表
    List<Announce> getAnnounceList();
    //查公告
    Announce getAnnounce(long id);
}
