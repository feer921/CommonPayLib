package common.pay.sdk;

import com.tencent.mm.sdk.modelpay.PayReq;

import java.io.Serializable;

/**
 * User: fee(1176610771@qq.com)
 * Date: 2016-11-01
 * Time: 18:51
 * DESC: 能发起微信/支付宝 支付的订单信息接口,各APP在使用本库时，因为各自的服务端返回的微信、支付宝的支付订单信息数据
 * 本库不能统一，所以只要各APP的某个订单信息实体对象实现该接口即可
 */
public interface ICanPayOrderInfo extends Serializable {
    /**
     *
     * @return 微信订单支付请求对象
     */
    PayReq convert2WxPayReq();

    /**
     * 获取阿里--支付宝的订单信息
     * 该支付宝的订单信息规则需要参见
     * https://doc.open.alipay.com/doc2/detail?treeId=59&articleId=103662&docType=1
     * 上面链接中请求参数示例对应PayTask payTask = new PayTask(activity); payTask.pay(orderInfo,true);
     * 目前有新版本
     * https://doc.open.alipay.com/docs/doc.htm?spm=a219a.7629140.0.0.vvNnw2&treeId=204&articleId=105300&docType=1
     * 新版本的支付SDK对应PayTask payTask = new PayTask(activity); payTask.payV2(orderInfo,true);
     * @return
     */
    String getAlipayInfo();

    /**
     * 本次订单信息是否可以支付
     * @return
     */
    boolean canPayThisOrder();

    /**
     * 是否为支付宝的订单
     * @return
     */
    boolean isAliPayOrder();

    /**
     * 是否为微信支付的订单
     * @return
     */
    boolean isWxPayOrder();
}
