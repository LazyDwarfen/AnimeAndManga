import React from 'react';

import MangaBlock from './MangaBlockComponent';
import Button from 'react-bootstrap/Button';

class MangaTabComponent extends React.Component {
    constructor(props) {
        super(props);
    }
    render() {
        return( 
            <div style={{display: 'grid'}}>
             <Button variant="secondary" className="add" onClick={(e)=>{this.props.handleShowModal(e, 'manga', true)}}>add</Button>
                {this.props.mangaList.mangaList.map((item) => (
                    <MangaBlock key={item.id} manga={item} updateFunction={this.props.updateFunction} showModalFunction={this.props.handleShowModal} />
                ))}
            </div>
        );
    }
}
export default MangaTabComponent;   