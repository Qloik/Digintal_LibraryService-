package com.library.new_library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.library.new_library.entity.Notice;
import com.library.new_library.mapper.NoticeMapper;
import com.library.new_library.service.NoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fyf
 * @since 2022-11-22
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public boolean EditNotice(Object id, Object title, Object text, Object userid) {
        Notice notice = new Notice();
        notice.setTitle(title.toString());
        notice.setText(text.toString());
        notice.setUserId(userid.toString());

        UpdateWrapper<Notice> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("notice_id",id.toString());
        int update = noticeMapper.update(notice,updateWrapper);
        if (update>=1)
            return true;
        else
            return false;
    }

    @Override
    public boolean AddNotice(Object title, Object text, Object userid) {
        Notice notice = new Notice();
        notice.setTitle(title.toString());
        notice.setText(text.toString());
        notice.setUserId(userid.toString());
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyyMMddhhmmss");
        notice.setNoticeId(dateFormat.format(date));
        SimpleDateFormat dateFormat2= new SimpleDateFormat("yyyy-MM-dd");
        notice.setReleaseDate(dateFormat2.format(date));

        noticeMapper.insert(notice);
        return true;
    }

    @Override
    public boolean DeleteNotice(Object id) {
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("notice_id",id.toString());

        noticeMapper.delete(queryWrapper);
        return true;
    }
}
