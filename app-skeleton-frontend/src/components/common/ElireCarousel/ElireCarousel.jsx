import React from 'react'
import { Carousel, Image } from 'react-bootstrap'
import ReactMarkdown from 'react-markdown'
import './ElireCarousel.css'

const ElireCarousel = ({ items, markdown, ...rest }) =>
  <div {...rest}>
    <Carousel indicators={true} controls={false}>
      {items.map((item, index) =>
        <Carousel.Item key={index}>
          <Image style={{ width: 600, height: 235 }} src={item.img || 'data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7'} />
          <Carousel.Caption>
            <div className="row">
              <div className="col-md-11 carousel-titre">
                {item.titre}
              </div>
              <div className="col-md-1 carousel-date">
                {item.date}
              </div>
            </div>
            <div className="carousel-content">
              {
                markdown
                ? <ReactMarkdown source={item.text} />
                : <div dangerouslySetInnerHTML={{ __html: item.text }} />
              }
            </div>
          </Carousel.Caption>
        </Carousel.Item>
      )}
    </Carousel>
  </div>

ElireCarousel.defaultProps = {
  markdown: false
}

ElireCarousel.propTypes = {
  items: React.PropTypes.arrayOf(
    React.PropTypes.shape({
      titre: React.PropTypes.string,
      date: React.PropTypes.string,
      text: React.PropTypes.string,
      img: React.PropTypes.string
    })
  ),
  markdown: React.PropTypes.bool
}
export default ElireCarousel
