package com.virtusa.batchservice.config;

import com.virtusa.batchservice.entity.Customer;
import com.virtusa.batchservice.util.ViJobListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    //    Reader object
    @Bean
    public ItemReader<Customer> reader() {
        FlatFileItemReader<Customer> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("customers.csv"));
//        reader.setResource(new FileSystemResource("E:\\own\\batch-service\\src\\main\\resources\\customers.csv"));
//        reader.setResource(new UrlResource("customers.csv"));
        reader.setLineMapper(new DefaultLineMapper() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setDelimiter(DELIMITER_COMMA);
                setNames("customerId", "firstName", "age");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper() {{
                setTargetType(Customer.class);
            }});
        }});
        return reader;
    }

    //    Processor object
    @Bean
    public ItemProcessor<Customer, Customer> processor() {
//        return new CustomerProcessServiceImpl();
        return customer -> {
            int age = customer.getAge();
            if (age >= 10 && age < 20) {
                customer.setPoints(5);
            } else if (age >= 20 && age <= 30) {
                customer.setPoints(10);
            } else {
                customer.setPoints(0);
            }
            return customer;
        };
    }


    @Autowired
    private DataSource dataSource;

    //    writer class object
    @Bean
    public JdbcBatchItemWriter<Customer> writer() {
        JdbcBatchItemWriter<Customer> writer = new JdbcBatchItemWriter<>();
        writer.setDataSource(dataSource);
        writer.setSql("INSERT INTO customer(customer_id, first_name, age, points) " +
                "values (:customerId, :firstName, :age, :points)");
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        return writer;
    }

    //    listener class object
    @Bean
    public JobExecutionListener listener() {
        return new ViJobListener();

    }

    //    stepbuilderfacetory;
    @Autowired
    private StepBuilderFactory sf;

    //    step object
    @Bean
    public Step stepA() {
        return sf.get("stepA")// step name
                .<Customer, Customer> chunk(20) // <I,O> chunk
                .reader(reader()) // reader object
                .processor(processor()) // processor object
                .writer(writer()) // writer object
                .build();
    }

    //    jobBuilderFactory
    @Autowired
    private JobBuilderFactory jb;

    @Bean
    public Job jobA() {
        return jb.get("jobA")
                .incrementer(new RunIdIncrementer())
                .listener(listener())
                .start(stepA())
//                .next(stepB())
//                .next(stepC())
                .build();

    }
}
