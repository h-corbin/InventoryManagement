export class ExtendedInventory {
    warehouseId :number;
    itemId :number;
    quantity :number;
    location :String;
    warehouseName :String;
    itemName :String;
    itemDescription :String;

    constructor(warehouseId :number=0, itemId :number=0, quantity :number=0, location :String, warehouseName :String="", itemName :String="", itemDescription :String="") {
        this.warehouseId = warehouseId;
        this.itemId = itemId;
        this.location = location;
        this.quantity = quantity;
        this.warehouseName = warehouseName;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
    }
}