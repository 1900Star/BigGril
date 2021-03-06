package com.yibao.biggirl.mvp.webview;

import com.yibao.biggirl.model.favoriteweb.FavoriteDaoInterface;
import com.yibao.biggirl.model.favoriteweb.FavoriteWebBean;
import com.yibao.biggirl.model.favoriteweb.FavoriteWebDao;
import com.yibao.biggirl.mvp.favorite.FavoriteActivity;
import com.yibao.biggirl.mvp.favorite.FavoriteFragment;
import com.yibao.biggirl.mvp.gank.girl.GirlActivity;
import com.yibao.biggirl.util.Constants;

import java.util.List;

/**
 * Author：Sid
 * Des：${  FavoritActivity和WeibActivity共用WebPresenter，FavoritActivity需要 查询所有和
 * 取消收藏操作，WeibActivit需要：新增收藏、取消收藏、查询是否已经收藏 操作}
 * Time:2017/6/17 03:05
 *
 * @author Stran
 */
public class WebPresenter {
    private FavoriteWebDao mDao;
    private WebActivity mWebActivity;
    private FavoriteActivity mFavoriteActivity;
    private FavoriteFragment mFavoriteFragment;
    private GirlActivity mGirlActivity;

    public WebPresenter(GirlActivity girlActivity) {
        mGirlActivity = girlActivity;
        mDao = new FavoriteWebDao();
    }

    public WebPresenter(FavoriteActivity activity) {
        mFavoriteActivity = activity;
        mDao = new FavoriteWebDao();
    }

    public WebPresenter(FavoriteFragment favoriteFragment) {
        mFavoriteFragment = favoriteFragment;
        mDao = new FavoriteWebDao();
    }

    public WebPresenter(WebActivity activity) {
        mWebActivity = activity;
        mDao = new FavoriteWebDao();
    }

    public void insertFavorite(FavoriteWebBean favoriteBean) {
        String beanType = favoriteBean.getType();
        mDao.insertFavorite(favoriteBean, new FavoriteDaoInterface.InsertFavoriteCallBack() {
            @Override
            public void insertStatus(Long insertStatus) {
                if (beanType.equals(Constants.SING_GIRL) || (beanType.equals(Constants.MULTIPLE_GIRL) || (beanType.equals(Constants.WEB_URL)))) {
                    mGirlActivity.insertStatus(insertStatus);
                } else {
                    mWebActivity.insertStatus(insertStatus);
                }
            }
        });
    }

    //根据type(目前只有0和1)判断删除操作是来自于FavoriteFag<0>还是WebActivity<1> GirlActivivy <2>
    public void cancelFavorite(Long id, int type) {
        mDao.cancelFavorite(id, cancelId -> {
            if (type == 0) {
//                mGirlActivity.cancelStatus(cancelId);
            } else if (type == 2) {
                mGirlActivity.cancelStatus(cancelId);

            } else {

                mWebActivity.cancelStatus(cancelId);
            }
        });
    }

    public void updataFavorite(FavoriteWebBean bean) {
        mDao.updataFavorite(bean, list -> {
//            FavoriteWebBean data = list.get(0);

        });
    }

    public void queryAllFavorite() {
        mDao.queryAllFavorite(list -> mFavoriteFragment.queryAllFavorite(list));

    }

    public void queryFavoriteIsCollect(int pageType, String gankId) {
        mDao.quetyConditional(gankId, new FavoriteDaoInterface.QueryConditionalCallBack() {
            @Override
            public void conditionalQuery(List<FavoriteWebBean> list) {
                if (pageType == 1) {
                mWebActivity.queryFavoriteIsCollect(list);

                } else if (pageType == 2) {
                    mGirlActivity.queryFavoriteIsCollect(list);

                }
            }
        });

    }

    public void destroyView() {
        mFavoriteActivity = null;
        mFavoriteFragment = null;
        mGirlActivity = null;
        mWebActivity = null;
    }

}
