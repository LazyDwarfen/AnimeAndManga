import AbstractService from './AbstractService';

class MangaService extends AbstractService{
    constructor(){
        super("Manga");
    }
}

var mangaServiceSingleton;

function getMangaService(){
    if(mangaServiceSingleton == null)
        mangaServiceSingleton = new MangaService();
    return mangaServiceSingleton;
}

export default getMangaService();