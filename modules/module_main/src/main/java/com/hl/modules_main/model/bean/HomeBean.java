package com.hl.modules_main.model.bean;

import com.hl.anotation.NotProguard;
import com.hl.base_module.adapter.BaseMulDataModel;

import java.util.List;

@NotProguard
public class HomeBean {

    /**
     * curPage : 1
     * datas : [{"apkLink":"","audit":1,"author":"","canEdit":false,"chapterId":502,"chapterName":"自助","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":true,"id":13150,"link":"https://juejin.im/post/5ea7792e5188256da0323444#heading-4","niceDate":"4小时前","niceShareDate":"4小时前","origin":"","prefix":"","projectLink":"","publishTime":1588036993000,"selfVisible":0,"shareDate":1588036993000,"shareUser":"JsonChao","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"深度探索 Gradle 自动化构建技术（五、Gradle 插件架构实现原理剖析）","type":0,"userId":611,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"鸿洋","canEdit":false,"chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":true,"id":13162,"link":"https://mp.weixin.qq.com/s/Ynh9-7cfs3vVMxCmEMpRxQ","niceDate":"13小时前","niceShareDate":"1小时前","origin":"","prefix":"","projectLink":"","publishTime":1588003200000,"selfVisible":0,"shareDate":1588047028000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"暗黑模式？安卓适配一波","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"code小生","canEdit":false,"chapterId":414,"chapterName":"code小生","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":true,"id":13170,"link":"https://mp.weixin.qq.com/s/iogNs1Z_BJb4-ZgJXT_A0Q","niceDate":"13小时前","niceShareDate":"1小时前","origin":"","prefix":"","projectLink":"","publishTime":1588003200000,"selfVisible":0,"shareDate":1588047821000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/414/1"}],"title":"深入浅出 Android 屏幕刷新原理","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"程序亦非猿","canEdit":false,"chapterId":428,"chapterName":"程序亦非猿","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":true,"id":13177,"link":"https://mp.weixin.qq.com/s/me8k_afGA4zl0IuCk7mxOA","niceDate":"13小时前","niceShareDate":"38分钟前","origin":"","prefix":"","projectLink":"","publishTime":1588003200000,"selfVisible":0,"shareDate":1588049482000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/428/1"}],"title":"一文看懂 Java8 的 Lambda表达式！","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"郭霖","canEdit":false,"chapterId":409,"chapterName":"郭霖","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":true,"id":13195,"link":"https://mp.weixin.qq.com/s/lP-xB5yzpvqVEiA439euVQ","niceDate":"13小时前","niceShareDate":"32分钟前","origin":"","prefix":"","projectLink":"","publishTime":1588003200000,"selfVisible":0,"shareDate":1588049836000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/409/1"}],"title":"Java的值传递和引用传递，你真的搞清楚了吗？","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","canEdit":false,"chapterId":73,"chapterName":"面试相关","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":true,"id":13078,"link":"https://bthvi-leiqi.blog.csdn.net/article/details/80954639","niceDate":"13小时前","niceShareDate":"22小时前","origin":"","prefix":"","projectLink":"","publishTime":1588001563000,"selfVisible":0,"shareDate":1587969098000,"shareUser":"紫雾凌寒","superChapterId":196,"superChapterName":"热门专题","tags":[],"title":"Android学习&mdash;&mdash;手把手教你实现Android热修复","type":0,"userId":10853,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","canEdit":false,"chapterId":229,"chapterName":"AOP","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":true,"id":13118,"link":"https://juejin.im/post/5ea66d64f265da480836d2b2","niceDate":"13小时前","niceShareDate":"15小时前","origin":"","prefix":"","projectLink":"","publishTime":1588001541000,"selfVisible":0,"shareDate":1587994471000,"shareUser":"18818486691","superChapterId":227,"superChapterName":"注解 & 反射 & AOP","tags":[],"title":"一行代码解决安卓重复点击","type":0,"userId":61241,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","canEdit":false,"chapterId":73,"chapterName":"面试相关","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":true,"id":13123,"link":"https://juejin.im/post/5ea2ab266fb9a03c82234a9f","niceDate":"13小时前","niceShareDate":"15小时前","origin":"","prefix":"","projectLink":"","publishTime":1588001519000,"selfVisible":0,"shareDate":1587994604000,"shareUser":"18818486691","superChapterId":196,"superChapterName":"热门专题","tags":[],"title":"Android-Flutter面经","type":0,"userId":61241,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","canEdit":false,"chapterId":95,"chapterName":"相机Camera","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":true,"id":13127,"link":"https://juejin.im/post/5ea690adf265da480469b006","niceDate":"13小时前","niceShareDate":"15小时前","origin":"","prefix":"","projectLink":"","publishTime":1588001469000,"selfVisible":0,"shareDate":1587994679000,"shareUser":"18818486691","superChapterId":95,"superChapterName":"多媒体技术","tags":[],"title":"相机小白自定义Camera实践","type":0,"userId":61241,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","canEdit":false,"chapterId":76,"chapterName":"项目架构","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":true,"id":13144,"link":"https://www.jianshu.com/p/f08ed42a4e36","niceDate":"14小时前","niceShareDate":"14小时前","origin":"","prefix":"","projectLink":"","publishTime":1588001385000,"selfVisible":0,"shareDate":1588001332000,"shareUser":"鸿洋","superChapterId":196,"superChapterName":"热门专题","tags":[],"title":"也谈Android应用架构","type":0,"userId":2,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","canEdit":false,"chapterId":502,"chapterName":"自助","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":true,"id":13142,"link":"https://juejin.im/post/5ea6f2475188256d6e211b09","niceDate":"14小时前","niceShareDate":"14小时前","origin":"","prefix":"","projectLink":"","publishTime":1588000228000,"selfVisible":0,"shareDate":1588000228000,"shareUser":"xll","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"Gldie实现角图标（支持gif）不放弃ImageView的centerCrop","type":0,"userId":22903,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","canEdit":false,"chapterId":502,"chapterName":"自助","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":true,"id":13069,"link":"https://juejin.im/post/5e84c202f265da47dc4cc01f","niceDate":"1天前","niceShareDate":"1天前","origin":"","prefix":"","projectLink":"","publishTime":1587949104000,"selfVisible":0,"shareDate":1587949104000,"shareUser":"躬行之","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"Android Jetpack组件之Lifecycle-Aware组件使用及源码解析","type":0,"userId":23270,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"code小生","canEdit":false,"chapterId":414,"chapterName":"code小生","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"id":13168,"link":"https://mp.weixin.qq.com/s/Akduog4J5yjbdafrKLHsEg","niceDate":"1天前","niceShareDate":"1小时前","origin":"","prefix":"","projectLink":"","publishTime":1587916800000,"selfVisible":0,"shareDate":1588047788000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/414/1"}],"title":"探索 Android TDD 开发方法","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"code小生","canEdit":false,"chapterId":414,"chapterName":"code小生","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"id":13169,"link":"https://mp.weixin.qq.com/s/oZASKxSVUJ3dFdYHmihBkQ","niceDate":"1天前","niceShareDate":"1小时前","origin":"","prefix":"","projectLink":"","publishTime":1587916800000,"selfVisible":0,"shareDate":1588047804000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/414/1"}],"title":"10 分钟实现 Java 发送邮件功能","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"互联网侦察","canEdit":false,"chapterId":421,"chapterName":"互联网侦察","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"id":13181,"link":"https://mp.weixin.qq.com/s/T8vHrLWqXzPRWtuuvTC6NQ","niceDate":"1天前","niceShareDate":"37分钟前","origin":"","prefix":"","projectLink":"","publishTime":1587916800000,"selfVisible":0,"shareDate":1588049564000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/421/1"}],"title":"记一次 JAVA 的内存泄露分析","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"soulqw","canEdit":false,"chapterId":358,"chapterName":"项目基础功能","collect":false,"courseId":13,"desc":"之前有在您公众号上发表过原理，现在经过优化和迭代已经稳定运行它能做得：\r\n1.轻松替换方法调用之间的顺序关系\r\n2.提高代码可读性和可维护性","descMd":"","envelopePic":"https://www.wanandroid.com/resources/image/pc/default_project_img.jpg","fresh":false,"id":13066,"link":"https://www.wanandroid.com/blog/show/2748","niceDate":"1天前","niceShareDate":"1天前","origin":"","prefix":"","projectLink":"https://github.com/soulqw/WorkFlow","publishTime":1587912349000,"selfVisible":0,"shareDate":1587912349000,"shareUser":"","superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=358"}],"title":"WorkFlow-轻松低成本解决串行调用链之间的耦合","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"wangjianxiandev","canEdit":false,"chapterId":402,"chapterName":"跨平台应用","collect":false,"courseId":13,"desc":":fire:项目基于 Flutter 移动应用框架，采用 Dart 语言编写，打造体验较佳的WanAndroid客户端","descMd":"","envelopePic":"https://wanandroid.com/blogimgs/e4109fbf-47d9-461a-a87a-16f20cba974c.png","fresh":false,"id":13060,"link":"https://www.wanandroid.com/blog/show/2746","niceDate":"1天前","niceShareDate":"1天前","origin":"","prefix":"","projectLink":"https://github.com/wangjianxiandev/WanAndroidFlutter","publishTime":1587907057000,"selfVisible":0,"shareDate":1587907057000,"shareUser":"","superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=402"}],"title":"Hey Flutter: 体验较佳的WanAndroid Flutter客户端","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"yangsanning","canEdit":false,"chapterId":339,"chapterName":"K线图","collect":false,"courseId":13,"desc":"股票相关控件（分时图、自选股迷你分时图、资金趋势图），可用于自定义View的学习","descMd":"","envelopePic":"https://wanandroid.com/blogimgs/9176f386-2496-4692-85b8-fda7903c0816.png","fresh":false,"id":13061,"link":"https://www.wanandroid.com/blog/show/2747","niceDate":"1天前","niceShareDate":"1天前","origin":"","prefix":"","projectLink":"https://github.com/yangsanning/StockView","publishTime":1587907000000,"selfVisible":0,"shareDate":1587907000000,"shareUser":"","superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=339"}],"title":"股票控件StockView","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"AlbertShen0211","canEdit":false,"chapterId":385,"chapterName":"架构","collect":false,"courseId":13,"desc":"在Android架构组件基础上，融入Kotlin 协程+retrofit，模拟网络，使用MVVM架构全面快速开发。","descMd":"","envelopePic":"https://wanandroid.com/resources/image/pc/default_project_img.jpg","fresh":false,"id":13057,"link":"https://www.wanandroid.com/blog/show/2743","niceDate":"1天前","niceShareDate":"1天前","origin":"","prefix":"","projectLink":"https://github.com/AlbertShen0211/Android-architecture-components","publishTime":1587906911000,"selfVisible":0,"shareDate":1587906911000,"shareUser":"","superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=385"}],"title":"Android Jetpack 最佳开发姿势","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"zhpanvip","canEdit":false,"chapterId":400,"chapterName":"ViewPager","collect":false,"courseId":13,"desc":"兼容ViewPager与ViewPager2，支持多种指示器样式以及多种滑动样式。","descMd":"","envelopePic":"https://wanandroid.com/blogimgs/e5f6fc83-0a7d-4fea-9acc-1015ee500492.png","fresh":false,"id":13059,"link":"https://www.wanandroid.com/blog/show/2745","niceDate":"1天前","niceShareDate":"1天前","origin":"","prefix":"","projectLink":"https://github.com/zhpanvip/viewpagerindicator","publishTime":1587906829000,"selfVisible":0,"shareDate":1587906829000,"shareUser":"","superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=400"}],"title":"兼容ViewPager与ViewPager2，支持多种指示器样式以及多种滑动样式","type":0,"userId":-1,"visible":1,"zan":0}]
     * offset : 0
     * over : false
     * pageCount : 419
     * size : 20
     * total : 8376
     */
    private List<DatasBean> datas;

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    @NotProguard
    public static class DatasBean extends BaseMulDataModel{
        /**
         * apkLink :
         * audit : 1
         * author :
         * canEdit : false
         * chapterId : 502
         * chapterName : 自助
         * collect : false
         * courseId : 13
         * desc :
         * descMd :
         * envelopePic :
         * fresh : true
         * id : 13150
         * link : https://juejin.im/post/5ea7792e5188256da0323444#heading-4
         * niceDate : 4小时前
         * niceShareDate : 4小时前
         * origin :
         * prefix :
         * projectLink :
         * publishTime : 1588036993000
         * selfVisible : 0
         * shareDate : 1588036993000
         * shareUser : JsonChao
         * superChapterId : 494
         * superChapterName : 广场Tab
         * tags : []
         * title : 深度探索 Gradle 自动化构建技术（五、Gradle 插件架构实现原理剖析）
         * type : 0
         * userId : 611
         * visible : 1
         * zan : 0
         */

        private String apkLink;
        private int audit;
        private String author;
        private boolean canEdit;
        private int chapterId;
        private String chapterName;
        private boolean collect;
        private int courseId;
        private String desc;
        private String descMd;
        private String envelopePic;
        private boolean fresh;
        private int id;
        private String link;
        private String niceDate;
        private String niceShareDate;
        private String origin;
        private String prefix;
        private String projectLink;
        private long publishTime;
        private int selfVisible;
        private long shareDate;
        private String shareUser;
        private int superChapterId;
        private String superChapterName;
        private String title;
        private int type;
        private int userId;
        private int visible;
        private int zan;
        private List<?> tags;

        public String getApkLink() {
            return apkLink;
        }

        public void setApkLink(String apkLink) {
            this.apkLink = apkLink;
        }

        public int getAudit() {
            return audit;
        }

        public void setAudit(int audit) {
            this.audit = audit;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public boolean isCanEdit() {
            return canEdit;
        }

        public void setCanEdit(boolean canEdit) {
            this.canEdit = canEdit;
        }

        public int getChapterId() {
            return chapterId;
        }

        public void setChapterId(int chapterId) {
            this.chapterId = chapterId;
        }

        public String getChapterName() {
            return chapterName;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }

        public boolean isCollect() {
            return collect;
        }

        public void setCollect(boolean collect) {
            this.collect = collect;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getDescMd() {
            return descMd;
        }

        public void setDescMd(String descMd) {
            this.descMd = descMd;
        }

        public String getEnvelopePic() {
            return envelopePic;
        }

        public void setEnvelopePic(String envelopePic) {
            this.envelopePic = envelopePic;
        }

        public boolean isFresh() {
            return fresh;
        }

        public void setFresh(boolean fresh) {
            this.fresh = fresh;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getNiceDate() {
            return niceDate;
        }

        public void setNiceDate(String niceDate) {
            this.niceDate = niceDate;
        }

        public String getNiceShareDate() {
            return niceShareDate;
        }

        public void setNiceShareDate(String niceShareDate) {
            this.niceShareDate = niceShareDate;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public String getProjectLink() {
            return projectLink;
        }

        public void setProjectLink(String projectLink) {
            this.projectLink = projectLink;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public int getSelfVisible() {
            return selfVisible;
        }

        public void setSelfVisible(int selfVisible) {
            this.selfVisible = selfVisible;
        }

        public long getShareDate() {
            return shareDate;
        }

        public void setShareDate(long shareDate) {
            this.shareDate = shareDate;
        }

        public String getShareUser() {
            return shareUser;
        }

        public void setShareUser(String shareUser) {
            this.shareUser = shareUser;
        }

        public int getSuperChapterId() {
            return superChapterId;
        }

        public void setSuperChapterId(int superChapterId) {
            this.superChapterId = superChapterId;
        }

        public String getSuperChapterName() {
            return superChapterName;
        }

        public void setSuperChapterName(String superChapterName) {
            this.superChapterName = superChapterName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getVisible() {
            return visible;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }

        public int getZan() {
            return zan;
        }

        public void setZan(int zan) {
            this.zan = zan;
        }

        public List<?> getTags() {
            return tags;
        }

        public void setTags(List<?> tags) {
            this.tags = tags;
        }
    }
}
