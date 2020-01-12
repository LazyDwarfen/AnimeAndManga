class AbstractService{
    constructor(urlService) {
        this.urlService = "http://localhost:8080/" + urlService;
    }

    async create(data){
        data.id = 0;
        const response = await fetch(`${this.urlService}`, {
            method: 'POST',
            mode: 'cors',
            cache: 'no-cache',
            redirect: 'follow',
            headers: {
                'Content-Type': 'application/json'
            },
            referrer: 'no-referrer',
            body: JSON.stringify(data),
        });
        return await response.json();
    }

    async update(data){
        const response = await fetch(`${this.urlService}`, {
            method: 'PUT',
            mode: 'cors',
            cache: 'no-cache',
            redirect: 'follow',
            referrer: 'no-referrer',
            body: JSON.stringify(data),
        });
        return await response.json();
    }

    async delete(data){
        const response = await fetch(`${this.urlService}/${data.id}`, {
            method: 'DELETE',
            mode: 'cors',
            cache: 'no-cache',
            redirect: 'follow',
            referrer: 'no-referrer',
            body: JSON.stringify(data),
        });
        return await response.json();
    }

    async get(data){
        const response = await fetch(`${this.urlService}/${data.id}`, {
            method: 'GET',
            mode: 'cors',
            cache: 'no-cache',
            redirect: 'follow',
            referrer: 'no-referrer',
            body: JSON.stringify(data)
        });
        return await response.json();
    }

    async getAll(){
        const response = await fetch(`${this.urlService}`, {
            method: 'GET',
            mode: 'cors',
            cache: 'no-cache',
            redirect: 'follow',
            referrer: 'no-referrer'
        });
        return await response.json();
    }
    static testMehod(){
        return "OK";
    }
}
export default AbstractService;