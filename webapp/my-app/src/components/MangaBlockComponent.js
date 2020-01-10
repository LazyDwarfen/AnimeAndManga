import React from 'react';

import MangaService from '../services/MangaService';
import Button from 'react-bootstrap/Button';

class MangaBlockComponent extends React.Component {
    constructor(props) {
        super(props);
        this.deleteElement = this.deleteElement.bind(this);
    }
    
    deleteElement(e, elementId) {
        e.preventDefault();
        MangaService.delete({
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

    render() {
        return ( 
            <div style={{display: 'grid', gridTemplateColumns: '1fr', gridTemplateRows:  '1fr 1fr 1fr 300px 1fr', border: '1px inset black', borderRadius: '10px', padding: '10px'}}>
                {/* <div className = "Id" >{this.props.manga.id}</div>  */}
                <div className = "Name" >Name: {this.props.manga.name}</div> 
                <div className = "Genre" >Genre: {this.props.manga.genre}</div> 
                <div className = "Author" >Author: {this.props.manga.author}</div> 
                <img 
                    className = "Pic" 
                    src = {this.props.manga.picURL} 
                    style = {{maxWidth:'280px',
                        height: '300px'}}
                    alt = 'error'
                ></img> 
                {/* <Button variant="Light" className="edit" onClick={this.deleteElement}>edit</Button> */}
                <Button variant="Light" className="delete" onClick={(e)=>{this.deleteElement(e, this.props.manga.id)}}>delete</Button>
            </div>
        )
    }
}
export default MangaBlockComponent;