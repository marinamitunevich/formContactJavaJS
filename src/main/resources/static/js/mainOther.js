
document.addEventListener('DOMContentLoaded', function () {
    const contactWrapperDOM = document.querySelector("#contact-wrapper");
    const contactTemplateDOM = document.querySelector("#contact-template");

    const contactFormDOM = document.querySelector("#contact-form");




    const contactRenderer = new ContactRenderer(contactWrapperDOM, contactTemplateDOM);
    const contactService = new ContactService(contactRenderer);

    const contactListener = new ContactListener(contactFormDOM, contactService);
    contactFormDOM.addEventListener('click',contactListener);

    const contactWrapperListener = new ContactWrapperListener(contactWrapperDOM, contactFormDOM, contactService);
    contactWrapperDOM.addEventListener('click', contactWrapperListener);

});

class ContactRenderer {

    constructor(contactWrapperDOM, contactTemplateDOM) {
        this.contactWrapperDOM = contactWrapperDOM;
        this.contactTemplateDOM = contactTemplateDOM;
    }

    _renderContact(contact) {
        const newNode = this.contactTemplateDOM.cloneNode(true);

        newNode.querySelector('span[data-id="name"]').innerHTML = contact.firstName;
        newNode.querySelector('span[data-id="lastname"]').innerHTML = contact.lastName;

        newNode.classList.toggle("hide-element");

        newNode.setAttribute("id", `contact-${contact.id}`);

        this.contactWrapperDOM.append(newNode);
    }

    renderContacts(contacts) {
        for (const contact of contacts) {
            this._renderContact(contact);
        }
    }

    clearAll(){
        const liHeader = this.contactWrapperDOM.querySelector(".collection-header")
        this.contactWrapperDOM.innerHTML = "";
        this.contactWrapperDOM.append(liHeader);

    }
}

class ContactService {

    fakeContacts = [
        {firstName: "Max", lastName: "Mustermann", age: 25, id: 1},
        {firstName: "Vasja", lastName: "Pupkin", age: 18, id: 2},
        {firstName: "John", lastName: "Doe", age: 35, id: 3},
        {firstName: "Mark", lastName: "Schmidt", age: 43, id: 4},
        {firstName: "Anna", lastName: "Baumann", age: 34, id: 5}
    ];

    constructor(contactRenderer) {
        this.contactRenderer = contactRenderer;

        this.getAll();
    }

    getAll() {
        this.contactRenderer.renderContacts(this.fakeContacts);
    }

    remove(id) {


        for (const element of this.fakeContacts) {
            if("contact-" + String(element.id) === id){
                this.fakeContacts.splice(this.fakeContacts.indexOf(element),1);
            }
        }
        for (const idElement of this.fakeContacts) {
            console.log(idElement)
        }
    }

    add(contact) {

        contact.id = this.fakeContacts[this.fakeContacts.length-1].id + 1;
        console.log(contact.id);
        this.fakeContacts.push(contact);

        this.contactRenderer._renderContact(contact);

    }

    edit(contact) {

        for (let fakeContact of this.fakeContacts) {
            if(contact.id === "contact-"+ String(fakeContact.id)){
                fakeContact.firstName = contact.firstName;
                fakeContact.lastName = contact.lastName;
                fakeContact.age = contact.age;
            }
        }


        for (const idElement of this.fakeContacts) {
            console.log(idElement)
        }

    }
}

class ContactListener {

    constructor(contactFormDOM, contactService) {
        this.contactFormDom = contactFormDOM;
        this.contactService = contactService;

    }

    handleEvent(event){


        const aElement = event.target.closest('a');

        console.log(aElement);

        if(aElement != null) {
            const action = aElement.dataset.action;
            this[action](event);
        }



    }

    add(event){

        const contactForm = event.currentTarget;
        const  contact = {
            firstName : contactForm.elements.firstName.value,
            lastName : contactForm.elements.lastName.value,
            age : contactForm.elements.age.value,

        }

        this.contactService.add(contact);
        this.contactFormDom.reset();


    }
    save(event){
        console.log("save");
        const contactForm = event.currentTarget;

        const  contact = {
            firstName : contactForm.elements.firstName.value,
            lastName : contactForm.elements.lastName.value,
            age : contactForm.elements.age.value,
            id : contactForm.elements.id.value,
        }

        this.contactService.edit(contact);
        this.contactFormDom.reset();

        const elem = document.querySelector(`#${contact.id}`);
        elem.querySelector('span[data-id="name"]').innerHTML = contact.firstName;
        elem.querySelector('span[data-id="lastname"]').innerHTML = contact.firstName;

        const button = event.target.closest('a');
        button.classList.toggle("hide-element");
        button.previousElementSibling.classList.toggle("hide-element");
        button.nextElementSibling.classList.toggle("hide-element");




    }

    cancel(event){

        const button = event.target.closest('a');
        console.log(button);

        button.classList.toggle("hide-element");
        button.previousElementSibling.classList.toggle("hide-element");
        button.previousElementSibling.previousElementSibling.classList.toggle("hide-element");
        this.contactFormDom.reset();


    }


}

class ContactWrapperListener {
    constructor(contactWrapperDOM, contactFormDOM, contactService) {

        this.contactWrapperDOM = contactWrapperDOM;
        this.contactFormDOM = contactFormDOM;
        this.contactService = contactService;

    }

    handleEvent(event) {

        const aElement = event.target.closest('a');
        const action = aElement.dataset.action;
        this[action](event);

    }
    toggle(event){

    }

    remove(event){

        const elementToDelete = event.target.closest('li');
        elementToDelete.remove();
        this.contactService.remove(elementToDelete.id);



    }

    edit(event){

        const elementToEdit = event.target.closest('li');
        this.contactFormDOM.querySelector('a[data-action="save"]').classList.toggle("hide-element");
        this.contactFormDOM.querySelector('a[data-action="cancel"]').classList.toggle("hide-element");
        this.contactFormDOM.querySelector('a[data-action="add"]').classList.add("hide-element");

        for (let contactFormDOMElement of this.contactWrapperDOM) {
            console.log(contactFormDOMElement);
        }




        for (let fakeContact of this.contactService.fakeContacts) {
            if(elementToEdit.id === "contact-"+String(fakeContact.id)){

                this.contactFormDOM.elements.firstName.value = fakeContact.firstName;
                this.contactFormDOM.elements.lastName.value = fakeContact.lastName;
                this.contactFormDOM.elements.age.value = fakeContact.age;
                this.contactFormDOM.elements.id.value = elementToEdit.id;

            }

        }


    }




}

