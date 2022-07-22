export class Warehouse {
    id :number;
    name :String;
    address :String;
    capacity :number;
    volume :number;

    constructor(id :number=0, name :String="", address :String="", capacity :number=0, volume :number=0) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.volume = volume;
    }
}