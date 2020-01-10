import AbstractService from './AbstractService';

class StudioService extends AbstractService{
    constructor(){
        super("Studio");
    }
}

var studioServiceSingleton;

function getStudioService(){
    if(studioServiceSingleton == null)
        studioServiceSingleton = new StudioService();
    return studioServiceSingleton;
}

export default getStudioService();