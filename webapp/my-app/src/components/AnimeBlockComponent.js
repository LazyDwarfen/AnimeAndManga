import React from 'react';

import MangaService from '../services/MangaService';
import AnimeService from '../services/AnimeService';
import Button from 'react-bootstrap/Button';
import StudioService from '../services/StudioService';

class AnimeBlockComponent extends React.Component {


    constructor(props) {
        super(props);

        this.deleteElement.bind(this);
        this.updateElement.bind(this);
    }

    deleteElement(e, elementId) {
        AnimeService.delete({
            id: elementId
        }).then(
            (response) => {
                if(response.status === 'OK')
                    alert('succesfully deleted');
                else
                    alert(response.status);
                this.props.updateFunction();
            },
            (error) => {
                alert('error');
                console.log(error);
            }
        );
    }

    updateElement(e, animeElement) {
        let animeElementCopy = Object.assign({}, animeElement);
        if(animeElementCopy.manga != null ) 
            animeElementCopy.manga={value: animeElement.manga.id, label: animeElement.manga.name};
        if(animeElementCopy.studio != null ) 
            animeElementCopy.studio={value: animeElement.studio.id, label: animeElement.studio.name};
        this.props.showModalFunction(e, 'anime', false, animeElementCopy);
    }

    render() {
        return ( 
            <div style={{
                display: 'grid',
                gridTemplateRows: '1fr 1fr 1fr 1fr 1fr 300px 1fr', 
                border: '1px inset black', 
                borderRadius: '10px', 
                padding: '10px',
                maxWidth: '300px'}}>
                <div className = "Name" >Name: {this.props.anime.name}</div> 
                <div className = "Genre" >Genre: {this.props.anime.genre}</div> 
                <div className = "Ongoing" >Ongoing: {this.props.anime.ongoing ? 'yes' : 'no'}</div> 
                <div className = "Studio" >Studio: {this.props.anime.studio == null? 'N/A' : this.props.anime.studio.name}</div> 
                <div className = "Manga" >Manga: {this.props.anime.manga == null? 'N/A' : this.props.anime.manga.name}</div> 
                <img 
                    className = "Pic" 
                    src = {this.props.anime.picURL} 
                    style = {{maxWidth:'280px'}}
                    alt = 'error'
                ></img> 
                {/* <Button variant="Light" className="edit" onClick={(e)=>{this.updateElement(e, this.props.anime)}}>edit</Button> */}
                <Button variant="Light" className="delete" onClick={(e)=>{this.deleteElement(e, this.props.anime.id)}}>delete</Button>
            </div>
        )
    }
}
export default AnimeBlockComponent;