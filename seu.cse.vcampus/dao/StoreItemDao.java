package dao;
/**
 * 类 {@code StoreItemDao}提供对商品信息的增、删、改、查功能.
 *
 * @author Ruijin Zhang
 * @author Hanlong Liu
 *
 * @since 2023/9/1
 */
import db.DbHelper;
import vo.StoreItem;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StoreItemDao {
    public StoreItemDao(){}

    /**
     * addStoreItem方法根据Id插入商品.
     *
     * @param storeItem
     * @return 商品插入成功返回1，商品因插入名称已存在插入失败返回-1，其他原因插入失败返回0.
     */
    public static Byte addStoreItem(StoreItem storeItem){
        String siid=storeItem.get_itemID();
        String siname=storeItem.get_itemName();
        float sipri=storeItem.get_itemPri();
        short sicnt=storeItem.get_itemCnt();
        String sqlString="insert into tblStoreItem(siId,siName,siPri,siCnt) values('"+siid+"','"+siname+"','"+sipri+"','"+sicnt+"')";
        if(queryStoreItemById(siid)!=null){
            //商品id已存在
            return -1;
        }
        else {
            try{
                DbHelper.executeNonQuery(sqlString);
                //插入成功
                return 1;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        //插入失败
        return 0;
    }

    /**
     * delStoreItem方法根据Id删除商品.
     *
     * @param siid
     * @return 商品删除成功返回1，商品因不存在而删除失败返回-1，其他原因返回0.
     */
    public static Byte delStoreItem(String siid){

        String sqlString="delete * from tblStoreItem where siId = '"+siid+"'";
        try{
            if (queryStoreItemById(siid)!=null){
                //查询有此商品，执行删除功能
                DbHelper.executeNonQuery(sqlString);
                return 1;
            }
            else {
                //数据不存在无法删除
                return -1;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * updateStoreItem方法实现商品信息更新，根据输入的StormItem的id唯一性进行修改.
     *
     * @param storeItem
     * @return 查询商品存在更新信息成功返回1，商品不存在返回-1，其他原因更新失败返回0.
     */
    public static Byte updateStoreItem(StoreItem storeItem){
        String siid=storeItem.get_itemID();
        String siname=storeItem.get_itemName();
        float sipri=storeItem.get_itemPri();
        short sicnt=storeItem.get_itemCnt();
        String sqlString="UPDATE tblStoreItem SET siId='"+siid+"',siName='"+siname+"',siPri='"+sipri+"',siCnt='"+sicnt+"' WHERE siId='"+siid+"'";
        try {
            if(queryStoreItemById(siid)!=null){
                //查询商品存在，执行更新
                DbHelper.executeNonQuery(sqlString);
                return 1;
            }
            //查询商品不存在，返回-1
            else return -1;
        }catch (Exception e){
            e.printStackTrace();
        }
        //更新失败
        return 0;
    }

    /**
     * queryStoreItemByName方法根据名称查询.
     *
     * @param name
     * @return 查询成功返回包含所有查询名称StoreItem的List，若查询不到返回null.
     */
    public static List<StoreItem> queryStoreItemByName(String name){
        String sqlString="select * from tblStoreItem";
        List<StoreItem> storeItem=new ArrayList<StoreItem>();
        try{
            ResultSet resultSet=DbHelper.executeQuery(sqlString);
            while (resultSet.next()){
                String tmpName=resultSet.getString("siName");
                if(tmpName.equals(name)) {
                    String siid = resultSet.getString("siId");
                    float sipri = resultSet.getFloat("siPri");
                    short sicnt = resultSet.getShort("siCnt");
                    StoreItem tmpStoreItem=new StoreItem(siid,tmpName,sipri,sicnt);
                    if(tmpStoreItem!=null){
                        storeItem.add(tmpStoreItem);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if(storeItem.isEmpty()){
            return null;
        }
        return storeItem;
    }

    /**
     * queryStoreItem方法根据id查询.
     *
     * @param id
     * @return 查询成功返回id相符的StoreItem，若查询不到返回null.
     */
    public static StoreItem queryStoreItemById(String id){
        String sqlString="select * from tblStoreItem";
        try {
            ResultSet resultSet=DbHelper.executeQuery(sqlString);
            while (resultSet.next()){
                String tmpId=resultSet.getString("siId");
                if(id.equals(tmpId)){
                    String siid=resultSet.getString("siId");
                    String siName=resultSet.getString("siName");
                    float siPri=resultSet.getFloat("siPri");
                    short siCnt=resultSet.getShort("siCnt");
                    StoreItem storeItem=new StoreItem(siid,siName,siPri,siCnt);
                    return storeItem;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 查询全部商品的 方法
     * @return 查询到的全部商品
     */
    public static ArrayList<StoreItem> queryAllStoreItems(){
        String sqlString="select*from tblStoreItem";
        ArrayList<StoreItem> storeItemArrayList=new ArrayList<>();
        try{
            ResultSet rs=DbHelper.executeQuery(sqlString);
            while(rs.next()){
                String siId=rs.getString("siId");
                StoreItem storeItem=queryStoreItemById(siId);
                if(storeItem!=null) {
                    storeItemArrayList.add(storeItem);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if(storeItemArrayList.isEmpty()){
            return null;
        }
        return storeItemArrayList;
    }
}
