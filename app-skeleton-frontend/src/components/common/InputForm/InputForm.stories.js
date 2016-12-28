import React from 'react'
import { storiesOf } from '@kadira/storybook'
import Form from './Form'
import Input from './Input'
import Select from './Select'
import Button from './Button'
import DatePicker from './DatePicker'


storiesOf('Form', module)
  .add('form simple', () => {
    let form,
      result

    const onClick = (e) => {
      result = form.getForm()
      alert(JSON.stringify(result))
      e.preventDefault()
    }

    const onChange = (e) => {
      console.log(e)
    }

    return (
      <Form className="form-horizontal" form={(e) => { form = e }} onChange={onChange}>
        <div className="form-group">
          <label htmlFor="nom" className="col-sm-2 control-label">Nom</label>
          <div className="col-sm-10">
            <Input type="text" name="nom" className="form-control" validations={['required']} />
          </div>
        </div>
        <div className="form-group">
          <label htmlFor="type" className="col-sm-2 control-label">Type</label>
          <div className="col-sm-10">
            <Select name="type" className="form-control" validations={['required']} >
              <option>1</option>
              <option>2</option>
              <option>3</option>
            </Select>
          </div>
        </div>
        <div className="form-group">
          <label htmlFor="type" className="col-sm-2 control-label">Date</label>
          <div className="col-sm-10">
            <DatePicker name="date" className="form-control" validations={['required']} />
          </div>
        </div>
        <div className="form-group">
          <div className="col-sm-offset-2 col-sm-10">
            <Button type="submit" className="btn btn-default" onClick={onClick} form={form}>Enregistrer</Button>
          </div>
        </div>
      </Form>
    )
  })
  .add('form simple set value', () => {
      let form,
        result

      const onClick = (e) => {
        result = form.getForm()
        alert(JSON.stringify(result))
        e.preventDefault()
      }

      const onChange = (e) => {
        console.log(e)
      }


      const data = {
        nom:'test',
        type:'2',
        typeScrutin : {id:2,libelle:'test2'}
      }

      return (
        <Form className="form-horizontal" form={(e) => { form = e }} onChange={onChange} data-edit={data}>
          <div className="form-group">
            <label htmlFor="nom" className="col-sm-2 control-label">Nom</label>
            <div className="col-sm-10">
              <Input type="text" name="nom" className="form-control" validations={['required']} />
            </div>
          </div>

          <div className="form-group">
            <label htmlFor="type" className="col-sm-2 control-label">Type</label>
            <div className="col-sm-10">
              <Select name="type" className="form-control" validations={['required']} >
                <option>1</option>
                <option>2</option>
                <option>3</option>
              </Select>
            </div>
          </div>

          <div className="form-group">
            <label htmlFor="typeScrutin" className="col-sm-2 control-label">Type Scrutin</label>
            <div className="col-sm-10">
              <Select name="typeScrutin" className="form-control" validations={['required']} >
                <option value="1" label="test1" />
                <option value="2" label="test2" />
                <option value="3" label="test3" />
              </Select>
            </div>
          </div>


          <div className="form-group">
            <div className="col-sm-offset-2 col-sm-10">
              <Button type="submit" className="btn btn-default" onClick={onClick} form={form}>Enregistrer</Button>
            </div>
          </div>
        </Form>
      )
    })
