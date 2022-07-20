export class Warehouse {
    id :number;
    name :String;
    address :String;
    capacity :number;
    volume :number;

    constructor(id :number, name :String, address :String, capacity :number, volume :number) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.volume = volume;
    }
}