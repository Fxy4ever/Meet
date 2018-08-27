# 红岩移动安卓最终考核作品-知遇  


#### 你有多久没有看到满天的繁星，
#### 城市夜晚虚伪的光明遮住你的眼睛。
#### 你是否遇见一个人
#### 你是否知道他(她)的全部呢？

##### 更新与2018.8.26
<br> 
<br> 

#### 介绍：
这是一个基于谷歌Material Design创意的社交聊天软件<br> 

---

#### 心路历程：
对于实时聊天软件，我想做很久了。正好暑假网校考核期间能有此机会能够自己构思一个app的设计。着实很考验一个人的审美。<br>

---

#### 关于这款软件的功能：
- [x] 广场发布情感问题(支持文字语音图片)，可以利用自己的财富值去增加消息的热度值。

- [x] 每个问题、回复、聊天、用户、文件都是单独建表，方便查询。

- [x] 多个共享组件、过渡动画比较炫酷，都是特别设计。

- [x] 可以修改自己的个性签名，在粉丝关注列表中动态展现出来，能让他人知道此刻你的心情。

- [x] 支持用户之前的私信，可以发送文字、语音、图片，保存了聊天记录以及优化了加载策略。若进程不被系统杀掉，会接收到Notificaiton的提醒。

- [x] 安装包仅有4mb 十分精简

- [x] 页面设计自认为比较精美，符合当下的审美。


---

#### 使用到的技术：
- RxJava+RxPermission的结合 申请权限更加方便
- 软件前半部分使用了mvp模式，后半部分由于觉得聊天使用mvp太过于繁琐，转为mvvm架构，即viewModel+LiveData，外加Databinding，解耦更加彻底。
- EventBus的使用，使消息刷新更加简单，优化了代码量。
- Material Design的设计模式，界面UI更加好看。
- LeanCloud强大的后端支持
- Glide压缩图片策略，图片加载更加迅速。
- 感谢SmartRefreshLayout与Zhihu.matisse框架，节省了许多时间。

---

#### 软件部分截图：
<img width="300" height="560" src="https://github.com/fengxinyao1/Meet/tree/master/app/assets/about.jpg"/><br>

<img width="300" height="560" src="https://github.com/fengxinyao1/Meet/tree/master/app/assets/chat.jpg"/><br>

<img width="300" height="560" src="https://github.com/fengxinyao1/Meet/tree/master/app/assets/follower.jpg"/><br>

<img width="300" height="560" src="https://github.com/fengxinyao1/Meet/tree/master/app/assets/main.jpg"/><br>

<img width="300" height="560" src="https://github.com/fengxinyao1/Meet/tree/master/app/assets/msg.jpg"/><br>

<img width="300" height="560" src="https://github.com/fengxinyao1/Meet/tree/master/app/assets/user.jpg"/><br>

