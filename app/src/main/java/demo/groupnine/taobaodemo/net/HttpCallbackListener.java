package demo.groupnine.taobaodemo.net;

public interface HttpCallbackListener {

    void onFinish(Object responese);

    void onError(Exception e);
}
