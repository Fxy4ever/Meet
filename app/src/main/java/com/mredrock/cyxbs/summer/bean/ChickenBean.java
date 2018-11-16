package com.mredrock.cyxbs.summer.bean;

import java.util.List;

/**
 * create by:Fxymine4ever
 * time: 2018/11/14
 */
public class ChickenBean {

    /**
     * data : ["https://mp.weixin.qq.com/s/oqqPNlv5xQERljCDWOHRpA?mode=daytime","https://mp.weixin.qq.com/s/kRG_0FFh4vGTbPWTzh3ULg?mode=daytime","https://mp.weixin.qq.com/s/Jd6jQasDF22P_oHz24UNew?mode=daytime","https://mp.weixin.qq.com/s/fkgUy4eRJlrnyjwZ68CWkA?mode=daytime","https://mp.weixin.qq.com/s/nluhQWfvceqAypZ_8lSyuw?mode=daytime","https://mp.weixin.qq.com/s/kBIzquptB054C_4hFwQXuQ?mode=daytime","https://mp.weixin.qq.com/s/SzBkxSUQzss6YTJ8HUzgCw?mode=daytime","https://mp.weixin.qq.com/s/DixRCVvc1J1CDSvsUa913g?mode=daytime","https://mp.weixin.qq.com/s/w0aeHNW3GC4Mew76SqguKg?mode=daytime","https://mp.weixin.qq.com/s/YbYyA6cZXmwDMUbOhZgSgg?mode=daytime","https://mp.weixin.qq.com/s/DixRCVvc1J1CDSvsUa913g?mode=daytime"]
     * info : success
     * status : 200
     */

    private String info;
    private int status;
    private List<String> data;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
