export class Inventory {
    warehouseId :number;
    itemId :number;
    quantity :number;
    location :String;

    constructor(warehouseId :number=0, itemId :number=0, quantity :number=0, location :String="") {
        this.warehouseId = warehouseId;
        this.itemId = itemId;
        this.location = location;
        this.quantity = quantity;
    }
}