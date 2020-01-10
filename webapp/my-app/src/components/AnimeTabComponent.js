import React from 'react';

import AnimeBlock from './AnimeBlockComponent';
import Button from 'react-bootstrap/Button';

class AnimeTabComponent extends React.Component {
    constructor(props) {
        super(props);
    }
    render() {
        return( 
            <div style={{display: 'grid'}}>
            <Button variant="secondary" className="add" onClick={(e)=>{this.props.handleShowModal(e, 'anime', true)}}>add</Button>
                {this.props.animeList.animeList.map((item) => (
                    <AnimeBlock key={item.id} anime={item} updateFunction={this.props.updateFunction} showModalFunction={this.props.handleShowModal}/>
                ))}
            </div>
        );
    }
}
export default AnimeTabComponent;   