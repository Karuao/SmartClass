package team.qdu.smartclass.activity;

/**
 * Created by 11602 on 2018/3/17.
 */

public interface DeleteFiles {

    //删除压缩后的生成的图片
    public void deleteCompressFiles();

    //删除无用的缓存文件
    abstract public void deleteCacheFiles();
}
