package vo;

/**
 * 类{@code StoreItem}商品类，封装着商品信息
 *
 * @author Ruijin Zhang
 * @author Hanlong Liu
 *
 * @since 2023/8/30
 */
public class StoreItem {
    String _itemID;
    String _itemName;
    Float _itemPri;
    short _itemCnt;

    /**
     * 商店类的构造方法
     * @param itemID
     * @param itemName
     * @param itemPri
     * @param itemCnt
     */
    public StoreItem(String itemID,String itemName,float itemPri,short itemCnt){
        set_itemCnt(itemCnt);
        set_itemID(itemID);
        set_itemName(itemName);
        set_itemPri(itemPri);
    }

    /**
     * _itemID的set方法
     * @param _itemID
     */
    public void set_itemID(String _itemID) {
        this._itemID = _itemID;
    }

    /**
     * _itemCnt的set方法
     * @param _itemCnt
     */

    public void set_itemCnt(short _itemCnt) {
        this._itemCnt = _itemCnt;
    }

    /**
     * _itemName的set方法
     * @param _itemName
     */
    public void set_itemName(String _itemName) {
        this._itemName = _itemName;
    }

    /**
     * _itemPri的set方法
     * @param _itemPri
     */
    public void set_itemPri(Float _itemPri) {
        this._itemPri = _itemPri;
    }

    /**
     * _itemID的get方法
     * @return 返回String商品编号
     */
    public String get_itemID() {
        return _itemID;
    }

    /**
     * _itemPri的get方法
     * @return 返回float商品价格
     */
    public Float get_itemPri() {
        return _itemPri;
    }

    /**
     * _itemCnt的get方法
     * @return 返回short商品库存
     */
    public short get_itemCnt() {
        return _itemCnt;
    }

    /**
     * _itemName的get方法
     * @return 返回String商品名
     */
    public String get_itemName() {
        return _itemName;
    }
}
