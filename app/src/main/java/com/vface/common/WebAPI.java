package com.vface.common;

/**
 * 
 * @author don
 * 
 */
public class WebAPI {

	public static final String DOMAIN = "pss100.com";

	/**
	 * 接口根地址
	 */
//	private static final String API_URL = "http://openapi." + DOMAIN+ "/openapi/";
	private static final String API_URL = "http://192.168.1.127:9012/openapi/";

	/**
	 * User relation API URLs
	 * 
	 * @author don
	 * 
	 */
	public class User {
		/**
		 * Login
		 */
		public static final String LOGIN_URL = API_URL + "User/Login";

        /**
         * 修改密码
         */
        public static final String CHANGE_PASSWORD_URL = API_URL + "User/ChangePassword";

	}

	public class Common {

        //检测是否有可升级的版本 GET
		public static final String CLIENT_UPGRADE_URL = API_URL + "Upgrade/ClientUpgradeNew/";//{deviceType}/{clientType}

        //发送推送Key POST
        public static final String SEND_PUSH_KEY_URL = API_URL + "StoreUser/ClientSetPushKeyForStoreUser";

        //获取接口调用凭证Accesstoken GET
        public static final String GetAccessToken = API_URL + "Base/GetAccessToken/mac34cv739opx367v/34dsd891283kjkasdncs20004ma89df0";//{}/{}

	}

    public class Shop{

        //商家详细信息 GET
        public static final String SHOP_DETAIL_INFO = API_URL + "Store/GetStoreById/";//{StoreId}

        //获取门店消费列表 GET
        @Deprecated
        public static final String SHOP_CONSUME_LIST_URL = API_URL + "Store/ExpenseList?";
//                +
//                "storeId=25fc3071-db62-11e4-b995-a0d3c1f0ddf4" +
//                "&phoneNumber=15912345678" +
//                "&endDate=2015-4-22" +
//                "&expenseType=1" +
//                "&pageIndex=1" +
//                "&pageSize=10" +
//                "&startDate=2015-4-21";
        
        public static final String SHOP_CONSUME_LIST_URLV2 = API_URL + "Store/ExpenseListV2?";
//        "/Store/ExpenseListV2?"
//        + "storeId=887ef9eb-5318-4990-ad35-7aef0899fa67"
//        + "&keywords=18626106221"
//        + "&endDate=2015-5-22"
//        + "&expenseType=0"
//        + "&pageIndex=1"
//        + "&pageSize=10"
//        + "&startDate=2015-5-1";

        //获取门店消费明细 GET
        public static final String SHOP_CONSUME_DETAIL_URL = API_URL + "Store/ExpenseDetail?";
//                "commodityExpenseId=f21ca333-afa6-4a93-b35c-80805cec216f" +
//                "&storeId=25fc3071-db62-11e4-b995-a0d3c1f0ddf4" +
//                "&expenseType=1";

        //获取门店商品列表 GET
        public static final String SHOP_COMMODITY_LIST_URL = API_URL + "Store/CommodityList?";
//                "storeId=25fc3071-db62-11e4-b995-a0d3c1f0ddf4" +
//                "&pageIndex=1" +
//                "&pageSize=10" +
//                "&commodityNameOrCode";

        public static final String SHOP_TOTAL_COMMODITY_URL = API_URL + "Store/GetTotalExpenseAmount";

        public static final String PEND_ORDER_URL = API_URL + "Store/PendOrder?";
//                "storeId=25fc3071-db62-11e4-b995-a0d3c1f0ddf4
//                 &storeMemberId=db442836-c094-4c61-a2f6-4c695f0632b5
//                &vfaceMemberCode=201504220953493
//                &Operator=admin
//                &orderDetailsJsonString=[{\"CommodityId\":\"2f49b499-41e2-4861-affe-eb5d7fb971ae\",\"Count\":2},{\"CommodityId\":\"92e974bb-8707-4cd1-87a5-f4c0e662725f\",\"Count\":3}]";


        public static final String SHOP_DATA_GLOSSARY= API_URL + "Store/GetStoreSummary?";
//                "storeId=25fc3071-db62-11e4-b995-a0d3c1f0ddf4" +
//                        "&startDate=2015-4-1 00:00:00" +
//                        "&endDate=2015-5-26 00:00:01";


    }

    public class Profile{

        //省份 GET
        public static final String GET_PROVINCE_URL = API_URL + "Common/GetProvince/";

        //城市 GET
        public static final String GET_CITY_URL = API_URL + "Common/GetCity/";//{ProvinceId}

        //区县 GET
        public static final String GET_DISTRICT_URL = API_URL + "Common/GetDistrict/";//{CityId}

        public static final String GET_MEMBER_GRADE = API_URL + "StoreMember/GetMemberGrade/";//{StoreId};
    }

    public class Member{

        //会员等级列表 GET
        public static final String GET_MEMBER_GRADE= API_URL + "StoreMember/GetMemberGrade/";//{StoreId}

        //获取会员基础信息 GET
        public static final String GET_STORE_MEMBER_INFO= API_URL + "StoreMember/GetStoreMemberInfo/";//{StoreMemberId}

        //会员信息(列表) GET
        public static final String GET_STORE_MEMBER_LIST = API_URL + "StoreMember/GetStoreMemberList/";//{pageIndex}/{pageSize}

        //当日会员到店(列表) GET
        public static final String GET_VISIT_RECORD = API_URL + "StoreMember/GetVisitRecord/";//{StoreId}

        //检查会员登记情况 GET
        public static final String CHECK_MEMBER_REGISTER = API_URL + "StoreMember/CheckMember/";//{StoreId}/{PhoneNumber}

        //创建会员 POST
        public static final String CREATE_MEMBER = API_URL + "StoreMember/CreateMember/";

        //会员修改接口 POST
        public static final String EDIT_MEMBER = API_URL + "StoreMember/EditMember/";

        //获取用户消费记录
        public static final String GET_USER_EXPENSE_LIST_URL = API_URL + "User/ExpenseList?";
//             vfaceMemberCode=201504121219122
            // &expenseType=1
            // &pageIndex=1
            // &pageSize=10
            // &storename=总店
            // &startDate=2015-1-1
            // &endDate=2016-1-1
    }

}
