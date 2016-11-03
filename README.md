# 库出何因？
现在市场上的各APP基本上都会集成微信、支付宝的支付了，好象不集成支付SDK让用户可以付付款就这家公司赚不到钱一样，呵呵！
如果公司就只有一个项目那么直接在项目的libs目录下加入微信、支付宝的支付SDK也就行了，也只需要维护一套代码。然而如果有两个以上的项目都需要
集成支付的情况下，就需要在每个项目的libs目录下都加入支付SDK并维护相应的代码，而代码基本上相同，当然使用写程序大法——Ctrl+C-->Ctrl+V,也可，
但是如果微信、支付宝的支付SDK更新改动了呢？又需要回到每个项目下换支付SDK、改代码... 停、停、停，作为聪明程序员们可不能这么干，
“偷懒”、代码无限次重复利用，永远是写代码的高等逼格与境界，程序员恨不得写一套代码，哪都能用！项目总是会越做越多，如果都要集成支付功能的话，
于是本着偷懒的原则，就很有必要把支付这种到处都相同的功能封装起来形成哪哪都能用的库项目，让APP开发者只关心支付的结果，如果微信、支付宝的SDK
更新了，更新此库就行，你继续只关心支付的结果而已，于是此库就这样被我码出来了，并且运用到公司的项目中，笔者也会随着微信、支付宝支付SDK的更新而更新；

# 微信、支付宝支付那事
  首先两个支付平台都不对个人提供服务了（以前能，想想那时一些个人通过这个东西，就能把钱付到个人的帐户下哈，那酸爽...）,现在都必须是商户帐户。
  支付宝支付的集成没什么好说的，挺友好，集成简单。
  可网上一搜微信支付那点事，说得比较多的就是微信支付那些坑，记得刚开始集成微信支付的时候，直接去的官网看资料以及Demo，心想集成一个SDK还不简单，
参考Demo来就是。可真正要完整稳当的运行集成好的微信支付还是要费点精力的，要不然也不会有那么多人抱怨微信支付的坑。
  ### 我也就举两个栗子：
  *1: 要想能发起微信支付，必须去[微信的开放平台](https://open.weixin.qq.com/)登陆商户的帐号，再为需要集成微信支付的APP建立档案然后提交审核(这期间一言不合就政审不通过),
再申请开通支付能力啊（又是一言不合就政审不通过啊！啊！啊！），过个一星期半月的等所有的都通过了，你才能真正使用微信支付了(嗯，开始能从用户那收钱了，我们程序员为这些个商户想想都有点小激动哈)。
好家伙，就是要这么繁琐、磨人！直接导致程序员在调用微信支付SDK的代码行间注释个//fuck the weixin!，好嘛，支付能力也开通了，SDK的代码也写好了，一跑程序，正预想着大方的给微信资助一毛五分的，
什么鬼？！都还没打开微信的大门呢，就被打回来了。（哈哈，又找到一个微信的BUG，APP崩溃了不会吧，嘿，别笑，我们的APP写得没微信好！），最后发现，噢，调个试，
也需要给项目打个正式的包啊，再次说出那世界通用口号:fuck，程序员本来挺单纯，现在都能出口成章(脏)，是Bug Bi的、是环境Bi的，是有原因的。。。
  *2: 微信的SDK集成好了，代码姿势参考官方Demo也写正确了，也打正式包了，那就来说说微信支付的坑。网上描述的那些坑，我倒是没有踩到过，不知道是不是自己人品问题还是微信的SDK已经修改好了，
我就来说说一个没多少人注意到的坑(微信支付团队果然NB，坑都隐藏得这么好)，手机内安装一个微信或者把之前微信的登陆帐号退出来，然后启动APP发起支付,这时调起
微信支付，微信会判断没有帐号登陆，则弹出登陆界面，就是这个界面，微信有考虑用户不去登陆啥也不做直接返回吗？？这时用户直接返回，到我们自己的APP界面(一般为支付过渡界面,说时尚点就是菊花一直转等待响应结果的界面),
程序员会发现，微信支付SDK什么也不返回！本应该返回支付结果的代码见：
‘public void onResp(BaseResp resp) {
   //....你倒是给老子响应啊
 }
’
就这样,说好的支付响应结果再也不见，让别人的APP里一直转的菊花情何以堪！？让用户一直看着菊花转那心理多煎熬！
不过后来，大概2016-10-1国庆节前，微信APP升级了一把(现在--2016-11-03，微信的版本为:6.3.28),这个坑被填好了，不过从多年开发经验以及善于测试的笔者来看，放心吧，还会有坑的！
总结一下微信、支付宝支付SDK，微信支付SDK就像一个矫情的小娘们一样，而支付宝支付SDK像个糙男实用男，随便你怎么用怎么玩，随便一个APP把它的SDK一集成就能付钱，只认钱不认APP。

# 本库的使用方法
上面扯了那么多，都TM不是重点，只是发一发程序员的牢骚，还是那句话，本库只让使用者只关心微信、支付宝支付的支付结果，一切有的坑都让本库承担。
这里才是重点，依赖本库，简单几步，就可以发起支付。

*1：本项目为Android的库工程，首先要让需要集成支付功能的APP项目依赖本库，我们都是有Android程序员身份的人，相信大家都会，后续把本库发布到[jitpack](https://jitpack.io)上去，让使用者能进行gradle依赖。
*2：依赖本库成功后,在自己APP项目里需要发起微信或者支付宝支付的地方调用本库的方法即可坐等支付结果，当然我们先来熟悉一遍整个支付流程：

a.: 首先APP的订单界面供用户[选择使用微信](https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=8_2)还是支付宝支付方式，这样好让APP的后台服务端给返回对应的订单信息。微信的订单信息如下(参考别人公司的):

>
'{
    "status": "1",
    "msg": "请求成功",
    "data": {
        "orderNo": "551515515151551",
        "appid": "wxbee9e8888665656",//这个是关键字段
        "partnerid": "01234598565",
        "prepayid": "wx20160122151438e832d724940443134124",//这个是关键字段
        "noncestr": "uphct75fl9qexvpeeiy8k0cdzo13h7ap",
        "timestamp": "1453446877",
        "package": "Sign=WXPay",
        "paySign": "1C71DBC9F32B41723165554987DD0F7"//这个是关键字段
    }
}'
对应于发起微信的支付，微信又矫情了一下，它需要相应的请求对象，见：
‘PayReq request = new PayReq();
     request.appId = "wxd930ea5d5a258f4f";

     request.partnerId = "1900000109";

     request.prepayId= "1101000000140415649af9fc314aa427",;

     request.packageValue = "Sign=WXPay";

     request.nonceStr= "1101000000140429eb40476f8896f4c9";

     request.timeStamp= "1398746574";

     request.sign= "7FFECB600D7157C5AA49810D2D8F28BC2811827B";
’
支付宝的支付订单信息(参考别人公司的)示例如下：
>
'
{
    "status": "1",
    "msg": "请求成功"，
    "data": {
        "orderNo": "2016011310211365556",
        "payInfo": "partner="2088101568358171"&seller_id="xxx@alipay.com"&out_trade_no="0819145412-6177"&subject="测试"&body="测试测试"&total_fee="0.01"&notify_url="http://notify.msp.hk/notify.htm"&service="mobile.securitypay.pay"&payment_type="1"&_input_charset="utf-8"&it_b_pay="30m"&sign="lBBK%2F0w5LOajrMrji7DUgEqNjIhQbidR13GovA5r3TgIbNqv231yC1NksLdw%2Ba3JnfHXoXuet6XNNHtn7VE%2BeCoRO1O%2BR1KugLrQEZMtG5jmJIe2pbjm%2F3kb%2FuGkpG%2BwYQYI51%2BhA3YBbvZHVQBYveBqK%2Bh8mUyb7GM1HxWs9k4%3D"&sign_type="RSA"
    }
}
'
支付宝的支付订单请求就是一字符串，参考官方[请求订单示例](https://doc.open.alipay.com/doc2/detail?treeId=59&articleId=103662&docType=1)
总之就是说，你们的APP能返回对应的满足微信或者支付宝的订单信息就行，而我们一般会把服务端的响应给解析成对应的Java实体对象，本库也不例外，
只要你所解析成的实体对象实现本库的接口[ICanPayOrderInfo](https://github.com/feer921/CommonPayLib/blob/master/src/main/java/common/pay/sdk/ICanPayOrderInfo.java)

b.:接下来调用:'PayEntryActivity.startPayActivity(Activity activity, ICanPayOrderInfo curPrePayOrderInfo, int requestCode)'方法，第一参数你认识它，它也认识你，不用多说，
第二个参数即为上面所提的你把服务端返回数据解析成对应的实体Java对象并实现了ICanPayOrderInfo接口的对象，可参考本库的PrePayOrderInfo类

c.:坐等本库的响应支付结果，您只需要在您进行步骤b的Activity界面的
'
 private static final int TEST_REQUEST_PAY_CODE = 100;
 @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
           //在该方法中，接收支付结果
           //参考
           switch (requestCode) {
                       case TEST_REQUEST_PAY_CODE:
                           String toastHint = "支付模式:%s,响应码:%s,结果描述:%s";
                           String payModeDesc = "未知";
                           String payRespCode = "unKnow";
                           if (data != null) {
                               int payMode = data.getIntExtra(CommonPayConfig.INTENT_KEY_CUR_PAY_MODE, CommonPayConfig.PAY_MODE_WX);
                               payModeDesc = payMode == CommonPayConfig.PAY_MODE_ALIPAY ? "[支付宝]" : "[微信]";
                                payRespCode = data.getStringExtra(CommonPayConfig.INTENT_KEY_REAL_PAY_RESULT_STATUS_CODE);
                           }
                           String resultDesc = "支付失败";
                           switch (resultCode) {
                               case CommonPayConfig.REQ_PAY_RESULT_CODE_OK:
                                   resultDesc = "支付成功";
                                   break;
                               case CommonPayConfig.REQ_PAY_RESULT_CODE_CANCEL:
                                   resultDesc = "支付被取消了";
                                   break;
                               case CommonPayConfig.RQE_PAY_RESULT_CODE_NO_WX:
                                   resultDesc = "支付失败,未安装微信APP";
                                   break;
                               case CommonPayConfig.RQE_PAY_RESULT_CODE_ERROR:
                                   resultDesc = "支付失败,";
                                   break;
                           }
                           toastShow(String.format(toastHint, payModeDesc, payRespCode, resultDesc));
                           break;
                   }
    }
'


