export class Item {
    id :number;
    name :String;
    description :String;
    size :number;

    constructor(id :number=0, name :String="", description :String="", size :number=0) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.size = size;
    }
}