class AbstractService{
    constructor(urlService) {
        this.urlService = "http://localhost:8080/" + urlService;
      }
      
    async create(data){
        const response = await fetch(`${this.urlService}?action=create`, {
            method: 'POST',
            mode: 'cors',
            cache: 'no-cache',
            redirect: 'follow',
            referrer: 'no-referrer',
            body: JSON.stringify(data),
        });
        return await response.json();
    }
    
    async update(data){
        const response = await fetch(`${this.urlService}?action=update`, {
            method: 'POST',
            mode: 'cors',
            cache: 'no-cache',
            redirect: 'follow',
            referrer: 'no-referrer',
            body: JSON.stringify(data),
        });
        return await response.json();
    }
    
    async delete(data){
        const response = await fetch(`${this.urlService}?action=delete`, {
            method: 'POST',
            mode: 'cors',
            cache: 'no-cache',
            redirect: 'follow',
            referrer: 'no-referrer',
            body: JSON.stringify(data),
        });
        return await response.json();
    }
    
    async get(data){
        const response = await fetch(`${this.urlService}?action=byID`, {
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
        const response = await fetch(`${this.urlService}?action=all`, {
            method: 'GET',
            mode: 'cors',
            cache: 'no-cache',
            redirect: 'follow',
            referrer: 'no-referrer'
        });
        return await response.json();
    }
    testMehod(){
        return "OK";
    }
}
export default AbstractService;