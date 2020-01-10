import React from 'react';

import StudioService from '../services/StudioService';
import Button from 'react-bootstrap/Button';

class StudioBlockComponent extends React.Component {
    constructor(props) {
        super(props);
        this.deleteElement = this.deleteElement.bind(this);
        this.updateElement = this.updateElement.bind(this);
    }

    deleteElement(e, elementId) {
        StudioService.delete({
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
            <div style={{display: 'grid', gridTemplateColumns: '1fr', gridTemplateRows: '1fr 1fr', border: '1px inset black', borderRadius: '10px', padding: '10px'}}>
                {/* <div className = "Id" >{this.props.studio.id}</div>  */}
                <div className = "Name" >{this.props.studio.name}</div>
                {/* <Button variant="Light" className="edit" onClick={this.deleteElement}>edit</Button> */}
                <Button variant="Light" className="delete" onClick={(e)=>{this.deleteElement(e, this.props.studio.id)}}>delete</Button>
            </div>
        )
    }
}
export default StudioBlockComponent;