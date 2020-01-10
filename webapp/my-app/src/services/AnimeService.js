import AbstractService from './AbstractService';

class AnimeService extends AbstractService{
    constructor(){
        super("Anime");
    }
}

var animeServiceSingleton;

function getAnimeService(){
    if(animeServiceSingleton == null)
        animeServiceSingleton = new AnimeService();
    return animeServiceSingleton;
}

export default getAnimeService();