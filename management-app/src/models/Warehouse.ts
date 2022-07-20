export class Warehouse {
    id :number;
    name :String;
    location :String;
    capacity :number;
    volume :number;

    constructor(id :number, name :String, location :String, capacity :number, volume :number) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.volume = volume;
    }
}