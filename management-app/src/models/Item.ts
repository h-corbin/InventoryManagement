export class Item {
    id :number;
    name :String;
    description :String;
    size :number;

    constructor(id :number, name :String, description :String, size :number) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.size = size;
    }
}