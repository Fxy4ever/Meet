package com.mredrock.cyxbs.summer.bean;

public class MsgType {

    /**
     * _lctype : -3
     * _lctext : sound1535217751.mp3
     * _lcfile : {"metaData":{"owner":"5b6c16d017d0090035afd2e4","duration":0.88,"_checksum":"e2b4cee66a9a60fcb78a3d9cb333cd12","size":4635,"_name":"sound1535217751.mp3","format":"mov,mp4,m4a,3gp,3g2,mj2"},"objId":"5b819059a22b9d0037b3a7e1","url":"http://lc-UYy61kgD.cn-n1.lcfile.com/AhqNVfsRfMYBN683ywisy2AKDWCEth8bfqRKN2af.mp3"}
     */

    private int _lctype;
    private String _lctext;
    private LcfileBean _lcfile;

    public int get_lctype() {
        return _lctype;
    }

    public void set_lctype(int _lctype) {
        this._lctype = _lctype;
    }

    public String get_lctext() {
        return _lctext;
    }

    public void set_lctext(String _lctext) {
        this._lctext = _lctext;
    }

    public LcfileBean get_lcfile() {
        return _lcfile;
    }

    public void set_lcfile(LcfileBean _lcfile) {
        this._lcfile = _lcfile;
    }

    public static class LcfileBean {
        /**
         * metaData : {"owner":"5b6c16d017d0090035afd2e4","duration":0.88,"_checksum":"e2b4cee66a9a60fcb78a3d9cb333cd12","size":4635,"_name":"sound1535217751.mp3","format":"mov,mp4,m4a,3gp,3g2,mj2"}
         * objId : 5b819059a22b9d0037b3a7e1
         * url : http://lc-UYy61kgD.cn-n1.lcfile.com/AhqNVfsRfMYBN683ywisy2AKDWCEth8bfqRKN2af.mp3
         */

        private MetaDataBean metaData;
        private String objId;
        private String url;

        public MetaDataBean getMetaData() {
            return metaData;
        }

        public void setMetaData(MetaDataBean metaData) {
            this.metaData = metaData;
        }

        public String getObjId() {
            return objId;
        }

        public void setObjId(String objId) {
            this.objId = objId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public static class MetaDataBean {
            /**
             * owner : 5b6c16d017d0090035afd2e4
             * duration : 0.88
             * _checksum : e2b4cee66a9a60fcb78a3d9cb333cd12
             * size : 4635
             * _name : sound1535217751.mp3
             * format : mov,mp4,m4a,3gp,3g2,mj2
             */

            private String owner;
            private double duration;
            private String _checksum;
            private int size;
            private String _name;
            private String format;

            public String getOwner() {
                return owner;
            }

            public void setOwner(String owner) {
                this.owner = owner;
            }

            public double getDuration() {
                return duration;
            }

            public void setDuration(double duration) {
                this.duration = duration;
            }

            public String get_checksum() {
                return _checksum;
            }

            public void set_checksum(String _checksum) {
                this._checksum = _checksum;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public String get_name() {
                return _name;
            }

            public void set_name(String _name) {
                this._name = _name;
            }

            public String getFormat() {
                return format;
            }

            public void setFormat(String format) {
                this.format = format;
            }
        }
    }
}
